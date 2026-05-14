package com.helpdesk.api.model.ticket;

import java.time.LocalDateTime;
import com.helpdesk.api.model.enums.ticket.TicketCategories;
import com.helpdesk.api.model.enums.ticket.TicketPriorities;
import com.helpdesk.api.model.enums.ticket.TicketStates;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "tickets")
@Getter
@Setter
@ToString
public class Ticket {

    // methods automatically
    @Setter(AccessLevel.NONE)
    private LocalDateTime createDate;

    @Setter(AccessLevel.NONE)
    @Id
    private String id;

    // methods of object
    private String title;
    private String description;
    private TicketCategories category;
    private TicketPriorities priority;
    private TicketStates state;

    // Constructors
    public Ticket() {
    }

    public Ticket(String title, String description, TicketCategories category, TicketPriorities priority) {
        this.createDate = LocalDateTime.now();
        this.title = title;
        this.description = description;
        this.category = category;
        this.priority = priority;
        this.state = TicketStates.OPEN;
    }
}
