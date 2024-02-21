package com.travellog.travellog.services.impl;

import com.travellog.travellog.configurations.ConversionConfiguration;
import com.travellog.travellog.dtos.CreateProfileDto;
import com.travellog.travellog.dtos.ProfileDetailDto;
import com.travellog.travellog.dtos.UpdateProfileDto;
import com.travellog.travellog.exceptions.EntityException;
import com.travellog.travellog.exceptions.UserException;
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
    public ProfileDetailDto findProfileByUserId(Integer userId) {
        Optional<Profile> profile = profileRepository.findByUserId(userId);
        if(profile.get() == null){
            throw new EntityException.NotFoundException("Profile");
        }
        ProfileDetailDto profileDetail = conversionConfiguration.convert(profile.get(), ProfileDetailDto.class);
        return profileDetail;
    }

    @Override
    public void deleteProfileById(User user) {
        if(user.getProfile() == null){
            throw new EntityException.NotFoundException("Profile");
        }
        Integer id = user.getProfile().getId();
        profileRepository.deleteById(id);
    }

    @Override
    public ProfileDetailDto updateProfile(User user, UpdateProfileDto updateProfileDto) {
        if(user.getProfile() == null){
            throw new EntityException.NotFoundException("Profile");
        }
        Profile profile = user.getProfile();
        if (updateProfileDto.getFirstName() != null) {
            profile.setFirstName(updateProfileDto.getFirstName());
        }
        if (updateProfileDto.getLastName() != null) {
            profile.setLastName(updateProfileDto.getLastName());
        }
        if (updateProfileDto.getMiddleName() != null) {
            profile.setMiddleName(updateProfileDto.getFirstName());
        }
        if (updateProfileDto.getSex() != null) {
            profile.setSex(updateProfileDto.getSex());
        }
        if (updateProfileDto.getImage() != null) {
            profile.setImage(updateProfileDto.getFirstName());
        }
        if (updateProfileDto.getAddressOne() != null) {
            profile.setAddressOne(updateProfileDto.getFirstName());
        }
        if (updateProfileDto.getAddressTwo() != null) {
            profile.setAddressTwo(updateProfileDto.getFirstName());
        }
        if (updateProfileDto.getState() != null) {
            profile.setState(updateProfileDto.getFirstName());
        }
        if (updateProfileDto.getZipCode() != null) {
            profile.setZipCode(updateProfileDto.getZipCode());
        }
        if (updateProfileDto.getCoverImage() != null) {
            profile.setCoverImage(updateProfileDto.getFirstName());
        }
        if (updateProfileDto.getBirthDate() != null) {
            profile.setBirthDate(updateProfileDto.getBirthDate());
        }
        profileRepository.save(profile);

        ProfileDetailDto finalProfile = conversionConfiguration.convert(profile, ProfileDetailDto.class);
        return finalProfile;
    }

    @Override
    public ProfileDetailDto createProfile(Integer userId, CreateProfileDto createProfileDto) {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()){
            throw new UserException.NotFoundException();
        }
        if(user.get().getProfile() != null){
            throw new EntityException.AlreadyExistsException("Profile");
        }
        Profile profile = conversionConfiguration.convert(createProfileDto, Profile.class);
        profile.setUser(user.get());
        Profile savedProfile = profileRepository.save(profile);
        return conversionConfiguration.convert(savedProfile, ProfileDetailDto.class);
    }
}
