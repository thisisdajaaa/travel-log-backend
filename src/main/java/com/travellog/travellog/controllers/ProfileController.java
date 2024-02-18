package com.travellog.travellog.controllers;

import com.travellog.travellog.dtos.CreateProfileDto;
import com.travellog.travellog.dtos.ProfileDetailDto;
import com.travellog.travellog.helpers.ResponseHelper;
import com.travellog.travellog.services.spec.IProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    private final IProfileService profileService;

    public ProfileController(IProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<ResponseHelper.CustomResponse<ProfileDetailDto>> createProfile(@Valid @RequestBody CreateProfileDto createProfileDto){
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully created profile!", profileService.createProfile(2,createProfileDto)),
                HttpStatus.CREATED);
    }

}
