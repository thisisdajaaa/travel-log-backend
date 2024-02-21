package com.travellog.travellog.services.spec;

import com.travellog.travellog.dtos.CreateProfileDto;
import com.travellog.travellog.dtos.ProfileDetailDto;
import com.travellog.travellog.dtos.UpdateProfileDto;
import com.travellog.travellog.models.User;

public interface IProfileService {
    ProfileDetailDto createProfile(Integer id, CreateProfileDto createProfileDto);
    ProfileDetailDto findProfileByUserId(Integer userId);
    void deleteProfileById(User user);

    ProfileDetailDto updateProfile(User user, UpdateProfileDto updateProfileDto);
}
