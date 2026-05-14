package com.helpdesk.api.model.commentary;

import com.helpdesk.api.model.entry.Profile;
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

    public SupportComment(Profile author, String description) {
        super(author, description);
        this.isVisibleToUser = true;
    }

    public SupportComment(Profile author, String description, boolean isVisibleToUser) {
        super(author, description);
        this.isVisibleToUser = isVisibleToUser;
    }
}

