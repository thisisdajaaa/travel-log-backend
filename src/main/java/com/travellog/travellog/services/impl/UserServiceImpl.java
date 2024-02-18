package com.travellog.travellog.services.impl;

import com.travellog.travellog.configurations.ConversionConfiguration;
import com.travellog.travellog.constants.RoleEnum;
import com.travellog.travellog.dtos.CreateUserDto;
import com.travellog.travellog.dtos.UserDetailDto;
import com.travellog.travellog.models.Role;
import com.travellog.travellog.models.User;
import com.travellog.travellog.repositories.IRoleRepository;
import com.travellog.travellog.repositories.IUserRepository;
import com.travellog.travellog.services.spec.IUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final ConversionConfiguration conversionConfiguration;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(IUserRepository userRepository, IRoleRepository roleRepository,
                           ConversionConfiguration conversionConfiguration, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.conversionConfiguration = conversionConfiguration;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetailDto createUser(CreateUserDto createUserDto) {
        User user = conversionConfiguration.convert(createUserDto, User.class);
        Role foundRole = roleRepository.findByRoleName(String.valueOf(RoleEnum.USER));

        user.setRole(foundRole);
        user.setPassword(bCryptPasswordEncoder.encode(createUserDto.getPassword()));

        User savedUser = userRepository.save(user);
        return conversionConfiguration.convert(savedUser, UserDetailDto.class);
    }

    @Override
    public List<UserDetailDto> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserDetailDto> formattedUsers = new ArrayList<>();

        for (User user : users) {
            formattedUsers.add(conversionConfiguration.convert(user, UserDetailDto.class));
        }

        return formattedUsers;
    }
}
