package com.travellog.travellog.controllers;

import com.travellog.travellog.dtos.AuthenticationDetailDto;
import com.travellog.travellog.dtos.CreateUserDto;
import com.travellog.travellog.dtos.LoginDto;
import com.travellog.travellog.helpers.ResponseHelper;
import com.travellog.travellog.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseHelper<AuthenticationDetailDto>> register(
            @Valid @RequestBody CreateUserDto createUserDto) {
        return new ResponseEntity<>(
                new ResponseHelper<>(true, "Successfully registered user!",
                        authenticationService.register(createUserDto)),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseHelper<AuthenticationDetailDto>> login(@Valid @RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(
                new ResponseHelper<>(true, "Successfully logged in user!",
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
