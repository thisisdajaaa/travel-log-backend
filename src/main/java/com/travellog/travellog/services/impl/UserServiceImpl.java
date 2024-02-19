package com.travellog.travellog.services.impl;

import com.travellog.travellog.configurations.ConversionConfiguration;
import com.travellog.travellog.constants.RoleEnum;
import com.travellog.travellog.dtos.user.CreateUserDto;
import com.travellog.travellog.dtos.user.UserDetailDto;
import com.travellog.travellog.exceptions.UserException;
import com.travellog.travellog.models.Role;
import com.travellog.travellog.models.User;
import com.travellog.travellog.repositories.IRoleRepository;
import com.travellog.travellog.repositories.IUserRepository;
import com.travellog.travellog.services.spec.IUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public UserDetailDto createUser(CreateUserDto createUserDto, RoleEnum roleEnum) {
        User user = conversionConfiguration.convert(createUserDto, User.class);

        Optional<User> foundUserByUsername = userRepository.findByUsername(createUserDto.getUsername());
        Optional<User> foundUserByEmail = userRepository.findByEmail(createUserDto.getEmail());

        if (foundUserByUsername.isPresent() || foundUserByEmail.isPresent()) {
            throw new UserException.AlreadyExistsException();
        }

        Role foundRole = roleRepository.findByName(String.valueOf(roleEnum));

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

    @Override
    public boolean isUserListEmpty() {
        return userRepository.count() == 0;
    }

    @Override
    public UserDetailDto getUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(UserException.NotFoundException::new);

        return conversionConfiguration.convert(user, UserDetailDto.class);
    }
}
