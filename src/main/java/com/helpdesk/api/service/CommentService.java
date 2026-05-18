package com.helpdesk.api.service;

import com.helpdesk.api.model.commentary.PublicComment;
import com.helpdesk.api.model.commentary.SupportComment;
import com.helpdesk.api.model.entry.Profile;
import com.helpdesk.api.model.ticket.Ticket;
import com.helpdesk.api.model.enums.ticket.TicketHistoryAction;
import com.helpdesk.api.repository.commentary.PublicCommentRepository;
import com.helpdesk.api.repository.commentary.SupportCommentRepository;
import com.helpdesk.api.repository.ticket.TicketHistoryRepository;
import com.helpdesk.api.repository.ticket.TicketRepository;
import com.helpdesk.api.model.ticket.TicketHistory;
import com.helpdesk.api.model.enums.entry.ProfileRoles;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {

    private final PublicCommentRepository publicCommentRepository;
    private final SupportCommentRepository supportCommentRepository;
    private final TicketRepository ticketRepository;
    private final TicketHistoryRepository ticketHistoryRepository;

    public CommentService(PublicCommentRepository publicCommentRepository, SupportCommentRepository supportCommentRepository, TicketRepository ticketRepository, TicketHistoryRepository ticketHistoryRepository) {
        this.publicCommentRepository = publicCommentRepository;
        this.supportCommentRepository = supportCommentRepository;
        this.ticketRepository = ticketRepository;
        this.ticketHistoryRepository = ticketHistoryRepository;
    }

    // Add a public comment to a ticket
    public void addComment(Ticket ticket, String text, Profile author) {
        Validation.validateUserExists(author);
        if (ticket == null || ticket.getId() == null || ticket.getId().isBlank()) {
            throw new IllegalArgumentException("Ticket is required and must have an id");
        }
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Comment text is required");
        }

        Ticket existing = ticketRepository.findById(ticket.getId())
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found with id: " + ticket.getId()));

        Validation.validateTicketNotClosed(existing);

        PublicComment comment = new PublicComment(author, text);
        publicCommentRepository.save(comment);

        // record history
        TicketHistory history = new TicketHistory(existing, TicketHistoryAction.PUBLIC_COMMENT_ADDED, LocalDateTime.now(), "Public comment added", author);
        ticketHistoryRepository.save(history);
    }

    // Add a support/internal comment to a ticket
    public void addSupportComment(Ticket ticket, String text, boolean isVisibleToUser, Profile author) {
        Validation.validateUserExists(author);
        if (ticket == null || ticket.getId() == null || ticket.getId().isBlank()) {
            throw new IllegalArgumentException("Ticket is required and must have an id");
        }
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Comment text is required");
        }

        Ticket existing = ticketRepository.findById(ticket.getId())
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found with id: " + ticket.getId()));

        Validation.validateTicketNotClosed(existing);

        // If the comment is internal (not visible to user) require support role or admin
        if (!isVisibleToUser) {
            Validation.validateUserRole(author, ProfileRoles.SUPPORT_AGENT);
        }

        SupportComment comment = new SupportComment(author, text, isVisibleToUser);
        supportCommentRepository.save(comment);

        // record history
        TicketHistory history = new TicketHistory(existing, TicketHistoryAction.SUPPORT_COMMENT_ADDED, LocalDateTime.now(), "Support comment added", author);
        ticketHistoryRepository.save(history);
    }
}

