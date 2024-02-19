package com.travellog.travellog.services.spec;

import com.travellog.travellog.constants.RoleEnum;
import com.travellog.travellog.dtos.user.CreateUserDto;
import com.travellog.travellog.dtos.user.UserDetailDto;

import java.util.List;

public interface IUserService {
    UserDetailDto createUser(CreateUserDto createUserDto, RoleEnum roleEnum);

    List<UserDetailDto> getUsers();

    boolean isUserListEmpty();

    UserDetailDto getUserById(Integer id);
}