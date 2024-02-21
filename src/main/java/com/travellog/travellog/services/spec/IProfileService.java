package com.travellog.travellog.services.spec;

import com.travellog.travellog.dtos.profile.CreateProfileDto;
import com.travellog.travellog.dtos.profile.ProfileDetailDto;
import com.travellog.travellog.dtos.profile.UpdateProfileDto;
import com.travellog.travellog.models.User;

public interface IProfileService {
    ProfileDetailDto createProfile(Integer id, CreateProfileDto createProfileDto);

    ProfileDetailDto findProfileByUserId(Integer userId);

    void deleteProfileById(User user);

    ProfileDetailDto updateProfile(User user, UpdateProfileDto updateProfileDto);
}
