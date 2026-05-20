package com.helpdesk.api.service;

import com.helpdesk.api.model.commentary.SupportComment;
import com.helpdesk.api.model.entry.Profile;
import com.helpdesk.api.model.enums.entry.ProfileRoles;
import com.helpdesk.api.model.ticket.Ticket;
import com.helpdesk.api.model.ticket.TicketFaq;
import com.helpdesk.api.repository.ticket.TicketFaqRepository;
import com.helpdesk.api.repository.ticket.TicketRepository;

import com.helpdesk.api.service.verifications.FAQValidation;
import com.helpdesk.api.service.verifications.Validation;
import org.springframework.stereotype.Service;

@Service
public class FAQService {

    private final TicketFaqRepository ticketFaqRepository;
    private final TicketRepository ticketRepository;

    public FAQService(TicketFaqRepository ticketFaqRepository, TicketRepository ticketRepository) {
        this.ticketFaqRepository = ticketFaqRepository;
        this.ticketRepository = ticketRepository;
    }

    // Publish an anonymous public FAQ item from a ticket and a selected support response.
    // Use this overload when you already have the admin context available.
    public TicketFaq publishToFAQ(Profile admin, Ticket ticket, SupportComment bestResponse) {
        Validation.validateUserExists(admin);
        Validation.validateUserRole(admin, ProfileRoles.ADMINISTRATOR);
        return publishToFAQ(ticket, bestResponse);
    }

    // Compatibility overload matching the requested signature.
    // The admin authorization should be enforced by the caller/controller.
    public TicketFaq publishToFAQ(Ticket ticket, SupportComment bestResponse) {
        FAQValidation.validateTicket(ticket);
        FAQValidation.validateComment(bestResponse);

        Validation.validateUserExists(bestResponse.getAuthor());
        Validation.validateUserRole(bestResponse.getAuthor(), ProfileRoles.SUPPORT_AGENT);

        // Load the persistent ticket to ensure it exists and to use the canonical reference
        Ticket existingTicket = ticketRepository.findById(ticket.getId()).orElseThrow(() -> new IllegalArgumentException("Ticket not found with id: " + ticket.getId()));

        // Ensure the selected comment belongs to the ticket
        if (bestResponse.getTicket() == null || bestResponse.getTicket().getId() == null || !existingTicket.getId().equals(bestResponse.getTicket().getId())) {
            throw new IllegalArgumentException("Selected response must belong to the same ticket");
        }

        // Resolve question and answer here (moved business logic out of model)
        // Pick the first non-null, non-blank candidate (title then description)
        String question = java.util.stream.Stream.of(existingTicket.getTitle(), existingTicket.getDescription()).filter(s -> s != null && !s.isBlank()).findFirst().orElse(null);
        FAQValidation.validateQuestion(question);

        String answer = bestResponse.getDescription();
        FAQValidation.validateAnswer(answer);

        // Find existing FAQ for the ticket or create a new one
        TicketFaq faq = ticketFaqRepository.findByTicket(existingTicket).orElse(null);
        if (faq == null) {
            faq = new TicketFaq(null, existingTicket, bestResponse, question, answer);
        }

        // Update fields on the data model (no business logic in model)
        faq.setTicket(existingTicket);
        faq.setSelectedComment(bestResponse);
        faq.setQuestion(question);
        faq.setAnswer(answer);

        return ticketFaqRepository.save(faq);
    }
}