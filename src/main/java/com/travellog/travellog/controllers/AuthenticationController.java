package com.travellog.travellog.controllers;

import com.travellog.travellog.dtos.authentication.AuthenticationDetailDto;
import com.travellog.travellog.dtos.user.CreateUserDto;
import com.travellog.travellog.dtos.authentication.LoginDto;
import com.travellog.travellog.helpers.ResponseHelper;
import com.travellog.travellog.services.spec.IAuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    public AuthenticationController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseHelper.CustomResponse<AuthenticationDetailDto>> register(
            @Valid @RequestBody CreateUserDto createUserDto) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully registered user!",
                        authenticationService.register(createUserDto)),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseHelper.CustomResponse<AuthenticationDetailDto>> login(
            @Valid @RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully logged in user!",
                        authenticationService.authenticate(loginDto)),
                HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}
