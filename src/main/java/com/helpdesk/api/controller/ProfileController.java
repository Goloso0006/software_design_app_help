package com.helpdesk.api.controller;

import com.helpdesk.api.dto.request.BlockProfileRequest;
import com.helpdesk.api.dto.response.OperationResponse;
import com.helpdesk.api.model.entry.Profile;
import com.helpdesk.api.repository.entry.ProfileRepository;
import com.helpdesk.api.service.AuthenticationService;
import com.helpdesk.api.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileRepository profileRepository;
    private final AuthenticationService authenticationService;
    private final ProfileService profileService;

    public ProfileController(ProfileRepository profileRepository, AuthenticationService authenticationService, ProfileService profileService) {
        this.profileRepository = profileRepository;
        this.authenticationService = authenticationService;
        this.profileService = profileService;
    }

    @PostMapping("/block")
    public ResponseEntity<OperationResponse> blockProfile(@RequestBody BlockProfileRequest req) {
        try {
            Profile admin = profileRepository.findById(req.adminId).orElseThrow(() -> new IllegalArgumentException("Admin profile not found"));
            authenticationService.blockProfile(admin, req.loginId);
            return ResponseEntity.ok(OperationResponse.ok("Profile blocked"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(OperationResponse.fail(e.getMessage()));
        }
    }

    @PostMapping("/set-status")
    public ResponseEntity<OperationResponse> setUserStatus(@RequestParam String loginId, @RequestParam boolean status) {
        try {
            profileService.setUserStatus(loginId, status);
            return ResponseEntity.ok(OperationResponse.ok("Status updated"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(OperationResponse.fail(e.getMessage()));
        }
    }
}

