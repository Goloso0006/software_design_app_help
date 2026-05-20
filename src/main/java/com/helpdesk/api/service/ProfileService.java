package com.helpdesk.api.service;

import com.helpdesk.api.model.entry.Login;
import com.helpdesk.api.model.entry.Profile;
import com.helpdesk.api.repository.entry.ProfileRepository;
import com.helpdesk.api.repository.entry.LoginRepository;

import static com.helpdesk.api.service.verifications.Validation.validateId;
import static com.helpdesk.api.service.verifications.Validation.validateUserExists;
import org.springframework.stereotype.Service;


@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final LoginRepository loginRepository;

    public ProfileService(ProfileRepository profileRepository, LoginRepository loginRepository) {
        this.profileRepository = profileRepository;
        this.loginRepository = loginRepository;
    }

    // Update profile data (name, lastName, phone, email)
    public void updateProfile(Profile data) {
        validateUserExists(data);

        Profile existing = profileRepository.findById(data.getId()).orElseThrow(() -> new IllegalArgumentException("Profile not found with id: " + data.getId()));

        if (data.getName() != null && !data.getName().isBlank()) {
            existing.setName(data.getName());
        }
        if (data.getLastName() != null && !data.getLastName().isBlank()) {
            existing.setLastName(data.getLastName());
        }
        if (data.getPhone() > 0) { // phone is int, so 0 or positive
            existing.setPhone(data.getPhone());
        }
        if (data.getEmail() != null && !data.getEmail().isBlank()) {
            existing.setEmail(data.getEmail());
        }

        // Note: role should not be updated here (admin only operation)
        profileRepository.save(existing);
    }

    // blocks and unblock a user
    public void setUserStatus(String loginId, boolean status) {
        validateId(loginId); // valida que no sea null ni vacío

        Login existing = loginRepository.findById(loginId)
                .orElseThrow(() -> new IllegalArgumentException("Login not found with id: " + loginId));

        existing.setState(status);
        loginRepository.save(existing);
    }
}