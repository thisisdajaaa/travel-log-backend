package com.travellog.travellog.services;

import com.travellog.travellog.dtos.CreateUserDto;
import com.travellog.travellog.dtos.UserDetailDto;
import java.util.List;

public interface UserService {
    UserDetailDto createUser(CreateUserDto createUserDto);

    List<UserDetailDto> getUsers();
}