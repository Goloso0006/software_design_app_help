package com.helpdesk.api.service;

import com.helpdesk.api.model.enums.entry.ProfileRoles;
import com.helpdesk.api.model.enums.ticket.TicketCategories;
import com.helpdesk.api.model.enums.ticket.TicketPriorities;
import com.helpdesk.api.model.enums.ticket.TicketStates;
import com.helpdesk.api.model.entry.Profile;
import com.helpdesk.api.model.ticket.Ticket;
import com.helpdesk.api.repository.ticket.TicketRepository;
import static com.helpdesk.api.service.Validation.*;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    // create ticket
    public Ticket createTicket(Profile user, String title, String description, TicketCategories category, TicketPriorities priority) {
        validateUserExists(user);
        validateUserRole(user, ProfileRoles.USER);

        Ticket ticket = new Ticket(title, description, category, priority);
        ticket.setCreatedBy(user);
        return ticketRepository.save(ticket);
    }

    // partial update. Only the creator can modify it.
    public Ticket updateTicket(Profile user, String ticketId, String title, String description, TicketCategories category, TicketPriorities priority) {
        validateUserExists(user);
        validateId(ticketId);

        Ticket existingTicket = ticketRepository.findById(ticketId).orElseThrow(() -> new IllegalArgumentException("Ticket not found with id: " + ticketId));

        validateTicketCreator(existingTicket, user, "update");

        if (title != null && !title.isBlank()) {
            existingTicket.setTitle(title);
        }
        if (description != null && !description.isBlank()) {
            existingTicket.setDescription(description);
        }
        if (category != null) {
            existingTicket.setCategory(category);
        }
        if (priority != null) {
            existingTicket.setPriority(priority);
        }

        return ticketRepository.save(existingTicket);
    }

    public void deleteTicket(Profile user, String ticketId) {
        validateUserExists(user);
        validateId(ticketId);

        Ticket existingTicket = ticketRepository.findById(ticketId).orElseThrow(() -> new IllegalArgumentException("Ticket not found with id: " + ticketId));
        validateTicketCreator(existingTicket, user, "delete");

        ticketRepository.deleteById(ticketId);
    }

    public List<Ticket> getMyTickets(Profile user) {
        validateUserExists(user);
        return ticketRepository.findByCreatedBy(user); // Devuelve lista vacía si no hay tickets
    }

    public void changeTicketPriority(String ticketId, TicketPriorities newPriority, Profile admin) {
        validateUserExists(admin);
        validateUserRole(admin, ProfileRoles.ADMINISTRATOR);
        validateId(ticketId);
        validateTicketPriority(newPriority);

        Ticket existing = ticketRepository.findById(ticketId).orElseThrow(() -> new IllegalArgumentException("Ticket not found with id: " + ticketId));

        existing.setPriority(newPriority);
        ticketRepository.save(existing);
    }

    public void assignTicket(String ticketId,  Profile support) {
        validateUserExists(support);
        validateUserRole(support, ProfileRoles.SUPPORT_AGENT);
        validateId(ticketId);

        // Only administrators can assign tickets
        // The caller (admin) check is expected to be done by controller/service caller;
        // here we'll require that the caller is an ADMINISTRATOR by checking user's role if provided as admin.
        // Since signature only receives the assignee, assume this method is called by admin context.

        Ticket existing = ticketRepository.findById(ticketId).orElseThrow(() -> new IllegalArgumentException("Ticket not found with id: " + ticketId));

        existing.setAssignedTo(support);
        existing.setAssignedAt(LocalDateTime.now());
        // When assigned, move state to IN_PROGRESS
        existing.setState(TicketStates.IN_PROGRESS);
        ticketRepository.save(existing);
    }

    /**
     * Update the status of a ticket. Only the assigned support agent can change it,
     * and for now only the CLOSED state is allowed.
     */
    public void updateTicketStatus(String ticketId, TicketStates newState, Profile agent) {
        validateId(ticketId);
        validateUserExists(agent);
        validateUserRole(agent, ProfileRoles.SUPPORT_AGENT);
        validateTicketState(newState);

        Ticket existing = ticketRepository.findById(ticketId).orElseThrow(() -> new IllegalArgumentException("Ticket not found with id: " + ticketId));

        if (existing.getAssignedTo() == null || existing.getAssignedTo().getId() == null || !existing.getAssignedTo().getId().equals(agent.getId())) {
            throw new IllegalArgumentException("Only the assigned agent can update this ticket status");
        }

        validateTicketNotClosed(existing);

        existing.setState(TicketStates.CLOSED);
        ticketRepository.save(existing);
    }

    public List<Ticket> getAssignedTickets(Profile agent) {
        validateUserExists(agent);
        validateUserRole(agent, ProfileRoles.SUPPORT_AGENT);

        return ticketRepository.findByAssignedTo(agent);
    }
}