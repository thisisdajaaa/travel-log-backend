package com.travellog.travellog.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travellog.travellog.configurations.ConversionConfiguration;
import com.travellog.travellog.constants.RoleEnum;
import com.travellog.travellog.constants.TokenTypeEnum;
import com.travellog.travellog.dtos.AuthenticationDetailDto;
import com.travellog.travellog.dtos.CreateUserDto;
import com.travellog.travellog.dtos.LoginDto;
import com.travellog.travellog.exceptions.UserException;
import com.travellog.travellog.models.Role;
import com.travellog.travellog.models.Token;
import com.travellog.travellog.models.User;
import com.travellog.travellog.repositories.RoleRepository;
import com.travellog.travellog.repositories.TokenRepository;
import com.travellog.travellog.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

public interface AuthenticationService {
    AuthenticationDetailDto register(CreateUserDto createUserDto);
    AuthenticationDetailDto authenticate(LoginDto loginDto);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
