package com.travellog.travellog.services.spec;

import com.travellog.travellog.constants.RoleEnum;
import com.travellog.travellog.dtos.CreateUserDto;
import com.travellog.travellog.dtos.UserDetailDto;

import java.util.List;

public interface IUserService {
    UserDetailDto createUser(CreateUserDto createUserDto, RoleEnum roleEnum);

    List<UserDetailDto> getUsers();

    boolean isUserListEmpty();
}