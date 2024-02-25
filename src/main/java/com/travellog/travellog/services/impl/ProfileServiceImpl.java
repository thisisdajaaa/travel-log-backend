package com.travellog.travellog.services.impl;

import com.travellog.travellog.configurations.ConversionConfiguration;
import com.travellog.travellog.dtos.country.CountryDetailDto;
import com.travellog.travellog.dtos.profile.CreateProfileDto;
import com.travellog.travellog.dtos.profile.ProfileDetailDto;
import com.travellog.travellog.dtos.profile.UpdateProfileDto;
import com.travellog.travellog.exceptions.EntityException;
import com.travellog.travellog.exceptions.UserException;
import com.travellog.travellog.models.Country;
import com.travellog.travellog.models.Profile;
import com.travellog.travellog.models.User;
import com.travellog.travellog.repositories.IProfileRepository;
import com.travellog.travellog.repositories.IUserRepository;
import com.travellog.travellog.services.spec.ICountryService;
import com.travellog.travellog.services.spec.IProfileService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileServiceImpl implements IProfileService {
    private final IProfileRepository profileRepository;
    private final ConversionConfiguration conversionConfiguration;
    private final ICountryService countryService;
    private final IUserRepository userRepository;

    public ProfileServiceImpl(IProfileRepository profileRepository, ConversionConfiguration conversionConfiguration, ICountryService countryService, IUserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.conversionConfiguration = conversionConfiguration;
        this.countryService = countryService;
        this.userRepository = userRepository;
    }

    @Override
    public ProfileDetailDto findProfileByUserId(Integer userId) {
        Optional<Profile> foundProfile = profileRepository.findByUserId(userId);

        if(foundProfile.isEmpty())  throw new EntityException.NotFoundException("Profile");

        Profile profile = foundProfile.get();

        CountryDetailDto foundCountry = null;

        if (profile.getCountry() != null)
            foundCountry = conversionConfiguration.convert(countryService.getCountryById(profile.getCountry().getId()), CountryDetailDto.class);


        return ProfileDetailDto.builder()
                .id(profile.getId())
                .firstName(profile.getFirstName())
                .middleName(profile.getMiddleName())
                .lastName(profile.getLastName())
                .sex(profile.getSex())
                .addressOne(profile.getAddressOne())
                .addressTwo(profile.getAddressTwo())
                .state(profile.getState())
                .zipCode(profile.getZipcode())
                .country(foundCountry)
                .profilePhoto(profile.getProfilePhoto())
                .coverPhoto(profile.getCoverPhoto())
                .birthDate(profile.getBirthDate())
                .city(profile.getCity())
                .email(profile.getUser().getEmail())
                .username(profile.getUser().getUsername())
                .build();
    }

    @Override
    public void deleteProfileById(User user) {
        if(user.getProfile() == null) throw new EntityException.NotFoundException("Profile");

        Integer id = user.getProfile().getId();
        profileRepository.deleteById(id);
    }

    @Override
    public ProfileDetailDto updateProfile(User user, UpdateProfileDto updateProfileDto) {
        if(user.getProfile() == null) throw new EntityException.NotFoundException("Profile");

        Profile profile = user.getProfile();

        if (updateProfileDto.getFirstName() != null)  profile.setFirstName(updateProfileDto.getFirstName());
        if (updateProfileDto.getLastName() != null) profile.setLastName(updateProfileDto.getLastName());
        if (updateProfileDto.getMiddleName() != null) profile.setMiddleName(updateProfileDto.getFirstName());
        if (updateProfileDto.getSex() != null) profile.setSex(updateProfileDto.getSex());
        if (updateProfileDto.getProfilePhoto() != null) profile.setProfilePhoto(updateProfileDto.getProfilePhoto());
        if (updateProfileDto.getAddressOne() != null) profile.setAddressOne(updateProfileDto.getAddressOne());
        if (updateProfileDto.getAddressTwo() != null) profile.setAddressTwo(updateProfileDto.getAddressTwo());
        if (updateProfileDto.getState() != null) profile.setState(updateProfileDto.getState());
        if (updateProfileDto.getZipCode() != null) profile.setZipcode(updateProfileDto.getZipCode());
        if (updateProfileDto.getCoverPhoto() != null) profile.setCoverPhoto(updateProfileDto.getCoverPhoto());
        if (updateProfileDto.getBirthDate() != null) profile.setBirthDate(updateProfileDto.getBirthDate());

        profileRepository.save(profile);

        return conversionConfiguration.convert(profile, ProfileDetailDto.class);
    }

    @Override
    public ProfileDetailDto createProfile(Integer userId, CreateProfileDto createProfileDto) {
        User user = userRepository.findById(userId).orElseThrow(UserException.NotFoundException::new);

        if(user.getProfile() != null) throw new EntityException.AlreadyExistsException("Profile");

        Profile profile = conversionConfiguration.convert(createProfileDto, Profile.class);
        profile.setUser(user);
        Profile savedProfile = profileRepository.save(profile);

        return conversionConfiguration.convert(savedProfile, ProfileDetailDto.class);
    }
}
