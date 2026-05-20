package com.helpdesk.api.dto.request;

public class CreateSupportCommentRequest {
    public String ticketId;
    public String authorId;
    public String text;
    public boolean isVisibleToUser;

    public CreateSupportCommentRequest() {}
}



