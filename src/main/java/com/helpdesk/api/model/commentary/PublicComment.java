package com.helpdesk.api.model.commentary;


import com.helpdesk.api.model.entry.Profile;
import com.helpdesk.api.model.enums.commentary.CommentStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "publicComments")
@Getter
@Setter
@ToString(callSuper = true)
public class PublicComment extends Comment {

    // methods of object
    private int likes = 0;
    private boolean isReported;
    private CommentStatus status;

    // Constructors
    protected PublicComment() {
    }

    public PublicComment(Profile author, String description) {
        super(author, description);
        this.likes = 0;
        this.isReported = false;
        this.status = CommentStatus.ACTIVE;
    }
}

