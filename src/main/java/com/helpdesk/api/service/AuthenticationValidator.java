package com.helpdesk.api.service;

import com.helpdesk.api.model.entry.Login;

public class AuthenticationValidator {

    // Validar credenciales
    public static void validateCredentials (String oneToken, String twoToken) {
        if (oneToken == null || oneToken.isBlank() || twoToken == null || twoToken.isBlank()) {
            throw new IllegalArgumentException("Both values are required");
        }
    }

    // validar si en nulo o bacio
    public static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    // validar login no sea nulo
    public static void validateLogin(Login login) {
        if (login == null) {
            throw new IllegalArgumentException("Login is required");
        }
    }
}
