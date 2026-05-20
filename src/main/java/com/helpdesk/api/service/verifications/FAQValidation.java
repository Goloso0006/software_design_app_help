package com.helpdesk.api.service.verifications;

import com.helpdesk.api.model.commentary.SupportComment;
import com.helpdesk.api.model.ticket.Ticket;

public class FAQValidation {

    public static void validateTicket(Ticket ticket) {
        if (ticket == null || ticket.getId() == null || ticket.getId().isBlank()) {
            throw new IllegalArgumentException("Ticket is required and must have an id");
        }
    }

    public static void validateComment(SupportComment bestResponse) {
        if (bestResponse == null) {
            throw new IllegalArgumentException("Selected support response is required");
        }
    }

    public static void validateAnswer(String answer) {
        if (answer == null || answer.isBlank()) {
            throw new IllegalArgumentException("Selected comment must have an answer");
        }
    }

    public static void validateQuestion(String question) {
        if (question == null || question.isBlank()) {
            throw new IllegalArgumentException("Ticket must provide a question through title or description");
        }
    }
}
