package com.travellog.travellog.services.spec;

import com.travellog.travellog.dtos.CreateUserDto;
import com.travellog.travellog.dtos.UserDetailDto;

import java.util.List;

public interface IUserService {
    UserDetailDto createUser(CreateUserDto createUserDto);

    List<UserDetailDto> getUsers();
}