package com.travellog.travellog.services.spec;

import com.travellog.travellog.dtos.CreateProfileDto;
import com.travellog.travellog.dtos.ProfileDetailDto;

public interface IProfileService {
    ProfileDetailDto createProfile(Integer userId, CreateProfileDto createProfileDto);
}
