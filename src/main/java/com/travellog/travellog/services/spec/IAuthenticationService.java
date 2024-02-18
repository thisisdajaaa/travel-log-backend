package com.travellog.travellog.services.spec;

import com.travellog.travellog.dtos.AuthenticationDetailDto;
import com.travellog.travellog.dtos.CreateUserDto;
import com.travellog.travellog.dtos.LoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IAuthenticationService {
    AuthenticationDetailDto register(CreateUserDto createUserDto);

    AuthenticationDetailDto authenticate(LoginDto loginDto);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
