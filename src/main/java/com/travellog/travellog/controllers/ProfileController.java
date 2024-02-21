package com.travellog.travellog.controllers;

import com.travellog.travellog.dtos.profile.CreateProfileDto;
import com.travellog.travellog.dtos.profile.ProfileDetailDto;
import com.travellog.travellog.dtos.profile.UpdateProfileDto;
import com.travellog.travellog.helpers.ResponseHelper;
import com.travellog.travellog.models.User;
import com.travellog.travellog.services.spec.ICustomUserDetailsService;
import com.travellog.travellog.services.spec.IProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    private final IProfileService profileService;
    private final ICustomUserDetailsService userDetailService;

    public ProfileController(IProfileService profileService, ICustomUserDetailsService userDetailsService) {
        this.profileService = profileService;
        this.userDetailService = userDetailsService;
    }

    @PostMapping
    public ResponseEntity<ResponseHelper.CustomResponse<ProfileDetailDto>> createProfile(
            @Valid @RequestBody CreateProfileDto createProfileDto) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully created profile!",
                        profileService.createProfile(userDetailService.getAuthenticatedUser().getId(),
                                createProfileDto)),
                HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<ResponseHelper.CustomResponse<String>> deleteProfile() {
        User user = userDetailService.getAuthenticatedUser();

        profileService.deleteProfileById(user);

        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully deleted profile!", null),
                HttpStatus.NO_CONTENT);
    }

    @PatchMapping
    public ResponseEntity<ResponseHelper.CustomResponse<ProfileDetailDto>> updateProfile(
            @Valid @RequestBody UpdateProfileDto updateProfileDto) {
        User user = userDetailService.getAuthenticatedUser();
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully updated profile!",
                        profileService.updateProfile(user, updateProfileDto)),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseHelper.CustomResponse<ProfileDetailDto>> fetchProfileDetail() {
        User user = userDetailService.getAuthenticatedUser();

        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully fetched profile!",
                        profileService.findProfileByUserId(user.getId())),
                HttpStatus.OK);
    }

}
