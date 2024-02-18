package com.travellog.travellog.services.impl;

import com.travellog.travellog.configurations.ConversionConfiguration;
import com.travellog.travellog.constants.RoleEnum;
import com.travellog.travellog.dtos.CreateProfileDto;
import com.travellog.travellog.dtos.ProfileDetailDto;
import com.travellog.travellog.models.Profile;
import com.travellog.travellog.models.User;
import com.travellog.travellog.repositories.IProfileRepository;
import com.travellog.travellog.repositories.IUserRepository;
import com.travellog.travellog.services.spec.IProfileService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileServiceImpl implements IProfileService {
    private final IProfileRepository profileRepository;
    private final ConversionConfiguration conversionConfiguration;
    private final IUserRepository userRepository;

    public ProfileServiceImpl(IProfileRepository profileRepository, ConversionConfiguration conversionConfiguration, IUserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.conversionConfiguration = conversionConfiguration;
        this.userRepository = userRepository;
    }

    @Override
    public ProfileDetailDto createProfile(Integer userId, CreateProfileDto createProfileDto) {
        Optional<User> user = userRepository.findById(userId);
        System.out.println("User: " + user.get().getUsername());
        Profile profile = conversionConfiguration.convert(createProfileDto, Profile.class);
        System.out.println("Format Profile: " + profile.getFirstName());
        profile.setUser(user.get());
        System.out.println("User Profile: " + profile.getUser().getUsername());

        Profile savedProfile = null;

        try {
            savedProfile = profileRepository.save(profile);
            System.out.println("Saved Profile: " + savedProfile.getUser().getUsername());
            System.out.println(savedProfile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // ProfileDetailDto finalProfile = conversionConfiguration.convert(savedProfile, ProfileDetailDto.class);
        // return finalProfile;
        return conversionConfiguration.convert(savedProfile, ProfileDetailDto.class);
    }
}
