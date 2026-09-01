package com.liveinterviewer.user_service.dto;

import com.liveinterviewer.user_service.entity.ExperienceLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    private String phone;

    private String targetRole;

    private ExperienceLevel experienceLevel;

    @Size(max = 1000, message = "Bio must be under 1000 characters")
    private String bio;
}