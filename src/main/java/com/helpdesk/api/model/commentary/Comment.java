package com.helpdesk.api.model.commentary;

import java.time.LocalDateTime;

import com.helpdesk.api.model.entry.Profile;
import com.helpdesk.api.model.enums.commentary.CommentStatus;
import com.helpdesk.api.model.ticket.Ticket;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Getter
@ToString
public abstract class Comment {

    // method automatically
    @Id
    private String id;


    // methods of object
    @Setter
    private String description;
    @Setter
    private CommentStatus status;
    private LocalDateTime createDate;
    @DBRef
    private Ticket ticket;
    private Profile author;

    // Constructors
    protected Comment() {
    }

    protected Comment(Profile author, String description) {
        this(null, author, description);
    }

    protected Comment(Ticket ticket, Profile author, String description) {
        this.createDate = LocalDateTime.now();
        this.ticket = ticket;
        this.author = author;
        this.description = description;
        this.status = CommentStatus.ACTIVE;
    }
}
