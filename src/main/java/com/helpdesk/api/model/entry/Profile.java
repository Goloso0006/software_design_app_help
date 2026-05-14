package com.helpdesk.api.model.entry;

import com.helpdesk.api.model.enums.entry.ProfileRoles;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "profiles")
@Getter
@Setter
@ToString
public class Profile {

    // method automatically
    @Setter(AccessLevel.NONE)
    @Id
    private String id;

    // methods of object
    private String name;
    private String lastName;
    private int phone;
    private String email;
    private ProfileRoles role;

    // Constructors
    protected Profile() {
    }

    public Profile(ProfileRoles role) {
        this.role = role;
    }

    public Profile(String name, String lastName, int phone, String email, ProfileRoles role) {
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.role = ProfileRoles.USER;
    }
}
