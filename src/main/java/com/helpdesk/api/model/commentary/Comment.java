package com.helpdesk.api.model.commentary;

import java.time.LocalDateTime;

import com.helpdesk.api.model.entry.Profile;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter
@ToString
public abstract class Comment {

    // method automatically
    @Id
    private String id;


    // methods of object
    @Setter
    private String description;
    private LocalDateTime createDate;
    private Profile author;

    // Constructors
    protected Comment() {
    }

    protected Comment(Profile author, String description) {
        this.createDate = LocalDateTime.now();
        this.author = author;
        this.description = description;
    }
}
