package com.travellog.travellog.services;

import com.travellog.travellog.dtos.profile.CreateProfileDto;
import com.travellog.travellog.dtos.profile.ProfileDetailDto;
import com.travellog.travellog.dtos.profile.UpdateProfileDto;

public interface ProfileService {
    ProfileDetailDto findProfileByUserId(Integer userId);
    void deleteProfileById(Integer id);

    ProfileDetailDto updateProfile(Integer id, UpdateProfileDto updateProfileDto);
    ProfileDetailDto createProfile(Integer userId, CreateProfileDto createProfileDto);
}
