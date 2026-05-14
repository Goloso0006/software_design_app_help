package com.helpdesk.api.model.entry;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "logins")
@Getter
@Setter
@ToString
public class Login {

    // method automatically
    @Setter(AccessLevel.NONE)
    @Id
    private String id;

    // methods of object
    private String username;
    private String password;
    private boolean state;

    // Constructors
    protected Login() {
    }

    public Login(String username, String password, boolean state) {
        this.username = username;
        this.password = password;
        this.state = state;
    }
}
