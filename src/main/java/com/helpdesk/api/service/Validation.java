package com.helpdesk.api.service;

import com.helpdesk.api.model.entry.Profile;
import com.helpdesk.api.model.enums.entry.ProfileRoles;
import com.helpdesk.api.model.enums.ticket.TicketPriorities;
import com.helpdesk.api.model.enums.ticket.TicketStates;
import com.helpdesk.api.model.ticket.Ticket;

public class Validation {

    // Validar usuario exista y tenga ID
    public static void validateUserExists(Profile user) {
        if (user == null || user.getId() == null || user.getId().isBlank()) {
            throw new IllegalArgumentException("User is required and must have a valid id");
        }
    }

    // Validar usuario tenga el rol esperado
    public static void validateUserRole(Profile user, ProfileRoles expectedRole) {
        validateUserExists(user); // opcional, por seguridad
        if (user.getRole() != expectedRole) {
            throw new IllegalArgumentException("User must have role " + expectedRole + ", but has " + user.getRole());
        }
    }

    // Valida ID no sea null ni vacío
    public static void validateId(String ticketId) {
        if (ticketId == null || ticketId.isBlank()) {
            throw new IllegalArgumentException("Ticket id is required");
        }
    }

    // Valida TicketPriorities no sea null
    public static void validateTicketPriority(TicketPriorities priority) {
        if (priority == null ) {
            throw new IllegalArgumentException("Priority must be provided");
        }
    }

    // Valida TicketStates no sea null y solo de tipo closed
    public static void validateTicketState(TicketStates state) {
        if (state == null) {
            throw new IllegalArgumentException("New state must be provided");
        }
        if (state != TicketStates.CLOSED) {
            throw new IllegalArgumentException("For now, only CLOSED state is allowed");
        }
    }

    // Valida ticket no esté ya cerrado
    public static void validateTicketNotClosed(Ticket ticket) {
        if (ticket.getState() == TicketStates.CLOSED) {
            throw new IllegalArgumentException("Ticket is already closed");
        }
    }

    // Valida usuario sea el creador del ticket para realizar una acción específica
    public static void validateTicketCreator(Ticket ticket, Profile user, String typeAccion) {
        if (ticket.getCreatedBy() == null
                || ticket.getCreatedBy().getId() == null
                || !ticket.getCreatedBy().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Only the creator can " + typeAccion + " this ticket");
        }
    }
}