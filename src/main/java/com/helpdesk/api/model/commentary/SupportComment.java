package com.helpdesk.api.model.commentary;

import com.helpdesk.api.model.entry.Profile;
import com.helpdesk.api.model.ticket.Ticket;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "supportComments")
@Getter
@Setter
@ToString(callSuper = true)
public class SupportComment extends Comment {

    // method of object
    private boolean isVisibleToUser;

    // Constructors
    protected SupportComment() {
    }
    /**
     * Primary constructor. Keep a single explicit constructor to avoid many overloads.
     * Use this when creating a SupportComment providing all necessary fields.
     */
    public SupportComment(Ticket ticket, Profile author, String description, boolean isVisibleToUser) {
        super(ticket, author, description);
        this.isVisibleToUser = isVisibleToUser;
    }
}