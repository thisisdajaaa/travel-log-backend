package com.travellog.travellog.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travellog.travellog.configurations.ConversionConfiguration;
import com.travellog.travellog.constants.RoleEnum;
import com.travellog.travellog.constants.TokenTypeEnum;
import com.travellog.travellog.dtos.AuthenticationDetailDto;
import com.travellog.travellog.dtos.CreateUserDto;
import com.travellog.travellog.dtos.LoginDto;
import com.travellog.travellog.exceptions.UserException;
import com.travellog.travellog.helpers.ResponseHelper;
import com.travellog.travellog.models.Role;
import com.travellog.travellog.models.Token;
import com.travellog.travellog.models.User;
import com.travellog.travellog.repositories.IRoleRepository;
import com.travellog.travellog.repositories.ITokenRepository;
import com.travellog.travellog.repositories.IUserRepository;
import com.travellog.travellog.services.spec.IAuthenticationService;
import com.travellog.travellog.services.spec.ICustomUserDetailsService;
import com.travellog.travellog.services.spec.IJWTService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final IUserRepository userRepository;
    private final ITokenRepository tokenRepository;
    private final IRoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final IJWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ConversionConfiguration conversionConfiguration;
    private final ICustomUserDetailsService customUserDetailsService;

    public AuthenticationServiceImpl(IUserRepository userRepository, ITokenRepository tokenRepository,
                                     IRoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, IJWTService jwtService,
                                     AuthenticationManager authenticationManager, ConversionConfiguration conversionConfiguration,
                                     ICustomUserDetailsService customUserDetailsService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.conversionConfiguration = conversionConfiguration;
        this.customUserDetailsService = customUserDetailsService;
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenTypeEnum.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Integer id) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(id);

        if (validUserTokens.isEmpty())
            return;

        for (Token token : validUserTokens) {
            token.setExpired(true);
            token.setRevoked(true);
        }

        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public AuthenticationDetailDto register(CreateUserDto createUserDto) {
        Role foundRole = roleRepository.findByRoleName(String.valueOf(RoleEnum.USER));
        User user = conversionConfiguration.convert(createUserDto, User.class);

        user.setRole(foundRole);
        user.setPassword(bCryptPasswordEncoder.encode(createUserDto.getPassword()));

        User savedUser = userRepository.save(user);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(savedUser.getUsername());
        String jwtToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        saveUserToken(savedUser, jwtToken);

        return AuthenticationDetailDto.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
    }

    @Override
    public AuthenticationDetailDto authenticate(LoginDto loginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getIdentifier(), loginDto.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new UserException.NotFoundException("Incorrect username or password!");
        } catch (AuthenticationException ex) {
            throw new UserException.NotFoundException("Authentication failed!");
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginDto.getIdentifier());

        String jwtToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(UserException.NotFoundException::new);

        revokeAllUserTokens(user.getId());
        saveUserToken(user, jwtToken);

        return AuthenticationDetailDto.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            ResponseHelper.respondWithUnauthorizedError(response, "Invalid authorization header format!");
            return;
        }

        final String refreshToken = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail == null) {
            throw new UserException.NotFoundException("User email not found in token!");
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(UserException.NotFoundException::new);

        if (!jwtService.isTokenValid(refreshToken, userDetails)) {
            ResponseHelper.respondWithUnauthorizedError(response, "Refresh token is not valid.");
            return;
        }

        String jwtToken = jwtService.generateToken(userDetails);
        String newRefreshToken = jwtService.generateRefreshToken(userDetails);

        revokeAllUserTokens(user.getId());
        saveUserToken(user, jwtToken);

        AuthenticationDetailDto authResponse = AuthenticationDetailDto.builder()
                .accessToken(jwtToken)
                .refreshToken(newRefreshToken)
                .build();

        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
    }
}
