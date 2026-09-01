package com.liveinterviewer.user_service.controller;

import com.liveinterviewer.user_service.dto.UserProfileRequest;
import com.liveinterviewer.user_service.dto.UserProfileResponse;
import com.liveinterviewer.user_service.entity.UserProfile;
import com.liveinterviewer.user_service.repository.UserProfileRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileRepository userProfileRepository;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(Authentication authentication) {
        String email = authentication.getName();

        UserProfile profile = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Profile not set up yet"));

        return ResponseEntity.ok(toResponse(profile));
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileResponse> upsertMyProfile(Authentication authentication,
                                                               @Valid @RequestBody UserProfileRequest request) {
        String email = authentication.getName();

        UserProfile profile = userProfileRepository.findByEmail(email)
                .orElseGet(() -> UserProfile.builder().email(email).build());

        profile.setFullName(request.getFullName());
        profile.setPhone(request.getPhone());
        profile.setTargetRole(request.getTargetRole());
        profile.setExperienceLevel(request.getExperienceLevel());
        profile.setBio(request.getBio());

        UserProfile saved = userProfileRepository.save(profile);

        return ResponseEntity.ok(toResponse(saved));
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserProfileResponse> getProfileByEmail(@PathVariable String email) {
        UserProfile profile = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Profile not found for: " + email));

        return ResponseEntity.ok(toResponse(profile));
    }

    private UserProfileResponse toResponse(UserProfile profile) {
        return UserProfileResponse.builder()
                .id(profile.getId())
                .email(profile.getEmail())
                .fullName(profile.getFullName())
                .phone(profile.getPhone())
                .targetRole(profile.getTargetRole())
                .experienceLevel(profile.getExperienceLevel())
                .bio(profile.getBio())
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .build();
    }
}