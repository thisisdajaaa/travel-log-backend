package com.travellog.travellog.services.spec;

import com.travellog.travellog.dtos.authentication.AuthenticationDetailDto;
import com.travellog.travellog.dtos.user.CreateUserDto;
import com.travellog.travellog.dtos.authentication.LoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IAuthenticationService {
    AuthenticationDetailDto register(CreateUserDto createUserDto);

    AuthenticationDetailDto authenticate(LoginDto loginDto);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
