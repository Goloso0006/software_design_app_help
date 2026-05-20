package com.helpdesk.api.service;

import com.helpdesk.api.model.entry.Login;
import com.helpdesk.api.model.entry.Profile;
import com.helpdesk.api.model.enums.entry.ProfileRoles;
import com.helpdesk.api.repository.entry.LoginRepository;

import com.helpdesk.api.service.verifications.AuthenticationValidator;
import com.helpdesk.api.service.verifications.Validation;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.Optional;


@Service
public class AuthenticationService {

    private final LoginRepository loginRepository;

    public AuthenticationService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    // Validate credentials and activate the login state
    public boolean login(String username, String password) {
        AuthenticationValidator.validateCredentials(username, password);

        Optional<Login> optionalLogin = loginRepository.findByUsername(username);

        if (optionalLogin.isEmpty()) {
            return false; // usuario no encontrado
        }

        Login login = optionalLogin.get();

        if (!Objects.equals(login.getPassword(), password)) {
            return false; // contraseña incorrecta
        }

        // Login exitoso: activar estado
        login.setState(true);
        loginRepository.save(login);
        return true;
    }

    // Logout current active sessions
    public void logout(String username) {
        if (AuthenticationValidator.isBlank(username)) {
            throw new IllegalArgumentException("Username is required");
        }

        Optional<Login> optionalLogin = loginRepository.findByUsername(username);
        if (optionalLogin.isEmpty()) {
            return; // nothing to do
        }

        Login login = optionalLogin.get();
        if (!login.isState()) {
            return; // already logged out
        }

        login.setState(false);
        loginRepository.save(login);
    }

    // Change password for a login identified by username
    public boolean changePassword(String username, String currentPassword, String newPassword) {
        if (AuthenticationValidator.isBlank(username)) {
            throw new IllegalArgumentException("Username is required");
        }

        AuthenticationValidator.validateCredentials(currentPassword, newPassword);

        if (Objects.equals(currentPassword, newPassword)) {
            throw new IllegalArgumentException("New password must be different from current password");
        }

        Login existing = loginRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Login not found for username: " + username));

        if (!Objects.equals(existing.getPassword(), currentPassword)) {
            return false;
        }

        existing.setPassword(newPassword);
        loginRepository.save(existing);
        return true;
    }

    // Admin operation: block (deactivate) a login by id. Only admins can perform this.
    public void blockProfile(Profile admin, String loginId) {
        Validation.validateUserExists(admin);
        Validation.validateUserRole(admin, ProfileRoles.ADMINISTRATOR);
        Validation.validateId(loginId);

        Login existing = loginRepository.findById(loginId).orElseThrow(() -> new IllegalArgumentException("Login not found with id: " + loginId));

        existing.setState(false);
        loginRepository.save(existing);
    }

}