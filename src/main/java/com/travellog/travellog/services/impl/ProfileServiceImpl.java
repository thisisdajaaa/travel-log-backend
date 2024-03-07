package com.travellog.travellog.services.impl;

import com.travellog.travellog.configurations.ConversionConfiguration;
import com.travellog.travellog.dtos.address.AddressDetailDto;
import com.travellog.travellog.dtos.country.CountryDetailDto;
import com.travellog.travellog.dtos.profile.CreateProfileDto;
import com.travellog.travellog.dtos.profile.ProfileDetailDto;
import com.travellog.travellog.dtos.profile.UpdateProfileDto;
import com.travellog.travellog.exceptions.EntityException;
import com.travellog.travellog.exceptions.UserException;
import com.travellog.travellog.models.Address;
import com.travellog.travellog.models.Country;
import com.travellog.travellog.models.Profile;
import com.travellog.travellog.models.User;
import com.travellog.travellog.repositories.IAddressRepository;
import com.travellog.travellog.repositories.IProfileRepository;
import com.travellog.travellog.repositories.IUserRepository;
import com.travellog.travellog.services.spec.ICountryService;
import com.travellog.travellog.services.spec.IProfileService;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ProfileServiceImpl implements IProfileService {
    private final IProfileRepository profileRepository;
    private final IAddressRepository addressRepository;
    private final ConversionConfiguration conversionConfiguration;
    private final ICountryService countryService;
    private final IUserRepository userRepository;

    public ProfileServiceImpl(IProfileRepository profileRepository, IAddressRepository addressRepository, ConversionConfiguration conversionConfiguration, ICountryService countryService, IUserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.addressRepository = addressRepository;
        this.conversionConfiguration = conversionConfiguration;
        this.countryService = countryService;
        this.userRepository = userRepository;
    }

    @Override
    public ProfileDetailDto findProfileByUserId(Integer userId) {
        Optional<Profile> foundProfile = profileRepository.findByUserId(userId);

        if (foundProfile.isEmpty()) throw new EntityException.NotFoundException("Profile");

        Profile profile = foundProfile.get();
        Address address = profile.getAddress();
        CountryDetailDto foundCountry = null;

        if (address.getCountry() != null)
            foundCountry = conversionConfiguration.convert(countryService.getCountryById(address.getCountry().getId()), CountryDetailDto.class);

        AddressDetailDto addressDetailDto = AddressDetailDto.builder()
                .addressOne(address.getAddressOne())
                .addressTwo(address.getAddressTwo())
                .state(address.getState())
                .zipcode(address.getZipcode())
                .city(address.getCity())
                .country(foundCountry)
                .build();

        System.out.println("countryDetailDto: "+ addressDetailDto.getCountry());

        return ProfileDetailDto.builder()
                .id(profile.getId())
                .firstName(profile.getFirstName())
                .middleName(profile.getMiddleName())
                .lastName(profile.getLastName())
                .sex(profile.getSex())
                .addressDetail(addressDetailDto)
                .profilePhoto(profile.getProfilePhoto())
                .coverPhoto(profile.getCoverPhoto())
                .birthDate(profile.getBirthDate())
                .email(profile.getUser().getEmail())
                .username(profile.getUser().getUsername())
                .build();
    }

    @Override
    public void deleteProfileById(User user) {
        if (user.getProfile() == null) throw new EntityException.NotFoundException("Profile");

        Integer id = user.getProfile().getId();
        profileRepository.deleteById(id);
    }

    @Override
    public ProfileDetailDto updateProfile(User user, UpdateProfileDto updateProfileDto) {
        if (user.getProfile() == null) throw new EntityException.NotFoundException("Profile");

        Profile profile = user.getProfile();
        Address address = profile.getAddress();

        if (updateProfileDto.getFirstName() != null) profile.setFirstName(updateProfileDto.getFirstName());
        if (updateProfileDto.getLastName() != null) profile.setLastName(updateProfileDto.getLastName());
        if (updateProfileDto.getMiddleName() != null) profile.setMiddleName(updateProfileDto.getFirstName());
        if (updateProfileDto.getSex() != null) profile.setSex(updateProfileDto.getSex());
        if (updateProfileDto.getProfilePhoto() != null) profile.setProfilePhoto(updateProfileDto.getProfilePhoto());
        if (updateProfileDto.getAddressOne() != null) address.setAddressOne(updateProfileDto.getAddressOne());
        if (updateProfileDto.getAddressTwo() != null) address.setAddressTwo(updateProfileDto.getAddressTwo());
        if (updateProfileDto.getCity() != null) address.setCity(updateProfileDto.getCity());
        if (updateProfileDto.getState() != null) address.setState(updateProfileDto.getState());
        if (updateProfileDto.getZipCode() != null) address.setZipcode(updateProfileDto.getZipCode());
        if (updateProfileDto.getCoverPhoto() != null) profile.setCoverPhoto(updateProfileDto.getCoverPhoto());
        if (updateProfileDto.getBirthDate() != null) profile.setBirthDate(updateProfileDto.getBirthDate());

        CountryDetailDto countryDetailDto = null;

        if (updateProfileDto.getCountryId() != null) {
            Country foundCountry = conversionConfiguration.convert(countryService.getCountryById(updateProfileDto.getCountryId()), Country.class);
            countryDetailDto = countryService.getCountryById(updateProfileDto.getCountryId());
            address.setCountry(foundCountry);
        }

        profileRepository.save(profile);
        addressRepository.save(address);

        AddressDetailDto addressDetailDto = AddressDetailDto.builder()
                .addressOne(address.getAddressOne())
                .addressTwo(address.getAddressTwo())
                .state(address.getState())
                .zipcode(address.getZipcode())
                .city(address.getCity())
                .country(countryDetailDto)
                .build();

        return ProfileDetailDto.builder()
                .id(profile.getId())
                .firstName(profile.getFirstName())
                .middleName(profile.getMiddleName())
                .lastName(profile.getLastName())
                .sex(profile.getSex())
                .addressDetail(addressDetailDto)
                .profilePhoto(profile.getProfilePhoto())
                .coverPhoto(profile.getCoverPhoto())
                .birthDate(profile.getBirthDate())
                .email(profile.getUser().getEmail())
                .username(profile.getUser().getUsername())
                .build();
    }

    @Override
    public ProfileDetailDto createProfile(Integer userId, CreateProfileDto createProfileDto) {
        User user = userRepository.findById(userId).orElseThrow(UserException.NotFoundException::new);
        if (user.getProfile() != null) throw new EntityException.AlreadyExistsException("Profile");

        Country foundCountry = null;

        if (createProfileDto.getCountryId() != null) {
            foundCountry = conversionConfiguration.convert(countryService.getCountryById(createProfileDto.getCountryId()), Country.class);
        }

        Address address = Address.builder()
                .addressOne(createProfileDto.getAddressOne())
                .addressTwo(createProfileDto.getAddressTwo())
                .state(createProfileDto.getState())
                .city(createProfileDto.getCity())
                .zipcode(createProfileDto.getZipCode())
                .country(foundCountry)
                .build();

        address = addressRepository.save(address);

        Profile profile = Profile.builder()
                .user(user)
                .firstName(createProfileDto.getFirstName())
                .middleName(createProfileDto.getMiddleName())
                .lastName(createProfileDto.getLastName())
                .profilePhoto(createProfileDto.getProfilePhoto())
                .coverPhoto(createProfileDto.getCoverPhoto())
                .birthDate(createProfileDto.getBirthDate())
                .sex(createProfileDto.getSex())
                .address(address)
                .build();

        Profile savedProfile = profileRepository.save(profile);

        return conversionConfiguration.convert(savedProfile, ProfileDetailDto.class);
    }

}
