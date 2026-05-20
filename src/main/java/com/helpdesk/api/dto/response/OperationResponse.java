package com.helpdesk.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OperationResponse {
    private boolean success;
    private String message;

    public static OperationResponse ok(String message) {
        return new OperationResponse(true, message);
    }

    public static OperationResponse fail(String message) {
        return new OperationResponse(false, message);
    }
}

