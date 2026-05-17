package com.helpdesk.api.service;

import com.helpdesk.api.model.entry.Profile;
import com.helpdesk.api.model.enums.entry.ProfileRoles;
import com.helpdesk.api.model.enums.ticket.TicketCategories;
import com.helpdesk.api.model.enums.ticket.TicketPriorities;
import com.helpdesk.api.model.ticket.Ticket;
import com.helpdesk.api.repository.ticket.TicketRepository;
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
        if (user == null || user.getRole() != ProfileRoles.USER) {
            throw new IllegalArgumentException("Only users with role USER can create tickets");
        }

        Ticket ticket = new Ticket(title, description, category, priority);
        ticket.setCreatedBy(user);
        return ticketRepository.save(ticket);
    }

    // partial update. Only the creator can modify it.
    public Ticket updateTicket(Profile user, String ticketId, String title, String description,
                               TicketCategories category, TicketPriorities priority) {
        if (user == null || user.getId() == null || user.getId().isBlank()) {
            throw new IllegalArgumentException("User is required");
        }
        if (ticketId == null || ticketId.isBlank()) {
            throw new IllegalArgumentException("Ticket id is required");
        }

        Ticket existingTicket = ticketRepository.findById(ticketId).orElseThrow(() -> new IllegalArgumentException("Ticket not found with id: " + ticketId));

        if (existingTicket.getCreatedBy() == null || existingTicket.getCreatedBy().getId() == null || !existingTicket.getCreatedBy().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Only the creator can update this ticket");
        }

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
        if (user == null || user.getId() == null || user.getId().isBlank()) {
            throw new IllegalArgumentException("User is required");
        }
        if (ticketId == null || ticketId.isBlank()) {
            throw new IllegalArgumentException("Ticket id is required");
        }

        Ticket existingTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found with id: " + ticketId));

        if (existingTicket.getCreatedBy() == null || existingTicket.getCreatedBy().getId() == null || !existingTicket.getCreatedBy().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Only the creator can delete this ticket");
        }

        ticketRepository.deleteById(ticketId);
    }

    public List<Ticket> getMyTickets(Profile user) {
        if (user == null || user.getId() == null || user.getId().isBlank()) {
            throw new IllegalArgumentException("User is required");
        }
        return ticketRepository.findByCreatedBy(user); // Devuelve lista vacía si no hay tickets
    }
}