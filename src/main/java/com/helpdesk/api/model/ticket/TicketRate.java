package com.helpdesk.api.model.ticket;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ticketRates")
@Getter
@ToString
public class TicketRate {

    // method automatically
    @Id
    private String id;

    // methods of object
    private int score;
    private String feedback;

    // Constructors
    protected TicketRate() {
    }

    public TicketRate(int score) {
        this.score = score;
    }

    public TicketRate(String feedback, int score) {
        this.feedback = feedback;
        this.score = score;
    }
}
