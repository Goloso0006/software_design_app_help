package com.helpdesk.api.model.ticket;

import com.helpdesk.api.model.entry.Profile;
import com.helpdesk.api.model.enums.ticket.TicketHistoryAction;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Document(collection = "ticketHistories")
@Getter
@ToString
public class TicketHistory {

    // method automatically
    @Id
    private String id;

    // methods of object
    @DBRef
    private Ticket ticket;
    private LocalDateTime createDate;
    private TicketHistoryAction action;
    private String description;
    private Profile performedBy;

    // Constructors
    protected TicketHistory() {
    }

    public TicketHistory(Ticket ticket, TicketHistoryAction action, LocalDateTime createDate, String description, Profile performedBy) {
        this.ticket = ticket;
        this.action = action;
        this.createDate = createDate;
        this.description = description;
        this.performedBy = performedBy;
    }
}
