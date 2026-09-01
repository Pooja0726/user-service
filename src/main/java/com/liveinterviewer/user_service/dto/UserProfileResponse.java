package com.liveinterviewer.user_service.dto;

import com.liveinterviewer.user_service.entity.ExperienceLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor
public class UserProfileResponse {

    private Long id;
    private String email;
    private String fullName;
    private String phone;
    private String targetRole;
    private ExperienceLevel experienceLevel;
    private String bio;
    private Instant createdAt;
    private Instant updatedAt;
}