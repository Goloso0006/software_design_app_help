package com.helpdesk.api.model.ticket;

import com.helpdesk.api.model.commentary.SupportComment;
// ...existing imports...
import lombok.Getter;
// ...existing imports...
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ticketFaqs")
@Getter
@Setter
@ToString
public class TicketFaq {

    @Id
    private String id;

    @DBRef
    private Ticket ticket;

    @DBRef
    private SupportComment selectedComment;

    private String question;

    private String answer;
    // This is a simple data model: no business logic or validation here.

    // constructors
    protected TicketFaq() {
    }

    public TicketFaq(String id, Ticket ticket, SupportComment selectedComment, String question, String answer) {
        this.id = id;
        this.ticket = ticket;
        this.selectedComment = selectedComment;
        this.question = question;
        this.answer = answer;
    }
}