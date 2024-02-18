package com.travellog.travellog.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.travellog.travellog.configurations.ConversionConfiguration;
import com.travellog.travellog.dtos.profile.CreateProfileDto;
import com.travellog.travellog.dtos.profile.ProfileDetailDto;
import com.travellog.travellog.dtos.profile.UpdateProfileDto;
import com.travellog.travellog.models.Profile;
import com.travellog.travellog.models.User;
import com.travellog.travellog.repositories.ProfileRepository;
import com.travellog.travellog.repositories.UserRepository;
import com.travellog.travellog.services.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final ConversionConfiguration conversionConfiguration;
    private final UserRepository userRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository,
            UserRepository userRepository,
            ConversionConfiguration conversionConfiguration) {
        this.profileRepository = profileRepository;
        this.conversionConfiguration = conversionConfiguration;
        this.userRepository = userRepository;
    }

    @Override // TODO need to handle optional data
    public ProfileDetailDto findProfileByUserId(Integer userId) {
        Optional<Profile> profile = profileRepository.findByUserId(userId);
        if (!profile.isPresent())
            return null;
        ProfileDetailDto profileDetail = conversionConfiguration.convert(profile, ProfileDetailDto.class);
        return profileDetail;
    }

    @Override
    public void deleteProfileById(Integer id) {
        profileRepository.deleteById(id);
    }

    @Override
    public ProfileDetailDto updateProfile(Integer id, UpdateProfileDto updateProfileDto) {
        Profile profile = profileRepository.getReferenceById(id);
        // if(!profile.isPresent()){
        // throw new RuntimeException();
        // }else {
        // Profile newProfile = profile.get();

        if (updateProfileDto.getFirstName() != null) {
            profile.setFirstName(updateProfileDto.getFirstName());
        } else if (updateProfileDto.getLastName() != null) {
            profile.setLastName(updateProfileDto.getLastName());
        } else if (updateProfileDto.getMiddleName() != null) {
            profile.setMiddleName(updateProfileDto.getFirstName());
        } else if (updateProfileDto.getSex() != null) {
            profile.setSex(updateProfileDto.getSex());
        } else if (updateProfileDto.getImage() != null) {
            profile.setImage(updateProfileDto.getFirstName());
        } else if (updateProfileDto.getAddressOne() != null) {
            profile.setAddressOne(updateProfileDto.getFirstName());
        } else if (updateProfileDto.getAddressTwo() != null) {
            profile.setAddressTwo(updateProfileDto.getFirstName());
        } else if (updateProfileDto.getState() != null) {
            profile.setState(updateProfileDto.getFirstName());
        } else if (updateProfileDto.getZipCode() != null) {
            profile.setZipCode(updateProfileDto.getZipCode());
        } else if (updateProfileDto.getCoverImage() != null) {
            profile.setCoverImage(updateProfileDto.getFirstName());
        } else if (updateProfileDto.getBirthDate() != null) {
            profile.setBirthDate(updateProfileDto.getBirthDate());
        }
        profileRepository.save(profile);

        ProfileDetailDto finalProfile = conversionConfiguration.convert(profile, ProfileDetailDto.class);
        return finalProfile;
    }

    @Override
    public ProfileDetailDto createProfile(Integer userId, CreateProfileDto createProfileDto) {
        Optional<User> user = userRepository.findById(userId);
        Profile profile = conversionConfiguration.convert(createProfileDto, Profile.class);
        profile.setUser(user.get());
        Profile savedProfile = profileRepository.save(profile);
        // ProfileDetailDto finalProfile = conversionConfiguration.convert(savedProfile, ProfileDetailDto.class);
        // return finalProfile;
        return new ProfileDetailDto();
    }

}
