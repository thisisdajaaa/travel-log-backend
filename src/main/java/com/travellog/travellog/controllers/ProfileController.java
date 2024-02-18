package com.travellog.travellog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travellog.travellog.dtos.profile.CreateProfileDto;
import com.travellog.travellog.dtos.profile.ProfileDetailDto;
import com.travellog.travellog.dtos.profile.UpdateProfileDto;
import com.travellog.travellog.helpers.ResponseHelper;
import com.travellog.travellog.services.ProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseHelper<ProfileDetailDto>> fetchProfileDetail(@PathVariable @Valid Integer id){
        return new ResponseEntity<>(
                new ResponseHelper<>(true, "Successfully fetched profile!", profileService.findProfileByUserId(id)),
                HttpStatus.OK);
    }

    @PostMapping  
    public ResponseEntity<ResponseHelper<ProfileDetailDto>> createProfile(@Valid @RequestBody CreateProfileDto createProfileDto){
        return new ResponseEntity<>(
                new ResponseHelper<>(true, "Successfully created profile!", profileService.createProfile(1,createProfileDto)),
                HttpStatus.CREATED);
    }

    //TODO get id from jwt token for deleting one profile
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseHelper<String>> deleteProfile(@PathVariable Integer id){
        profileService.deleteProfileById(id);
        return new ResponseEntity<>(
                new ResponseHelper<>(true, "Successfully deleted role!", ""),
                HttpStatus.NO_CONTENT);
    }

    // TODO get id from jwt token for updating self profile
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseHelper<ProfileDetailDto>> updateProfile(@Valid @RequestBody UpdateProfileDto updateProfileDto,
     @PathVariable Integer id){
        return new ResponseEntity<>(
                new ResponseHelper<>(true, "Successfully updated profile!", profileService.updateProfile(id, updateProfileDto)),
                HttpStatus.OK);
    }



}
