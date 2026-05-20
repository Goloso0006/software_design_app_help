package com.helpdesk.api.dto.request;

public class CreateTicketRequest {
    public String userId;
    public String title;
    public String description;
    public String category; // enum name
    public String priority; // enum name

    public CreateTicketRequest() {}
}



