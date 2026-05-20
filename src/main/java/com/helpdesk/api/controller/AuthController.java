package com.helpdesk.api.controller;

import com.helpdesk.api.dto.request.ChangePasswordRequest;
import com.helpdesk.api.dto.request.LoginRequest;
import com.helpdesk.api.dto.response.OperationResponse;
import com.helpdesk.api.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<OperationResponse> login(@RequestBody LoginRequest req) {
        try {
            boolean ok = authenticationService.login(req.username, req.password);
            if (ok) return ResponseEntity.ok(OperationResponse.ok("Login successful"));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(OperationResponse.fail("Invalid credentials"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(OperationResponse.fail(e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<OperationResponse> logout(@RequestParam String username) {
        try {
            authenticationService.logout(username);
            return ResponseEntity.ok(OperationResponse.ok("Logged out"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(OperationResponse.fail(e.getMessage()));
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<OperationResponse> changePassword(@RequestBody ChangePasswordRequest req) {
        try {
            boolean ok = authenticationService.changePassword(req.username, req.currentPassword, req.newPassword);
            if (ok) return ResponseEntity.ok(OperationResponse.ok("Password changed"));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(OperationResponse.fail("Current password incorrect"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(OperationResponse.fail(e.getMessage()));
        }
    }
}

