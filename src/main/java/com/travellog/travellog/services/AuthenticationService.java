package com.travellog.travellog.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travellog.travellog.configurations.ConversionConfiguration;
import com.travellog.travellog.constants.RoleEnum;
import com.travellog.travellog.constants.TokenTypeEnum;
import com.travellog.travellog.dtos.AuthenticationDetailDto;
import com.travellog.travellog.dtos.CreateUserDto;
import com.travellog.travellog.dtos.LoginDto;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ConversionConfiguration conversionConfiguration;
    private final CustomUserDetailsService customUserDetailsService;


    @Autowired
    public AuthenticationService(UserRepository userRepository, TokenRepository tokenRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JWTService jwtService, AuthenticationManager authenticationManager, ConversionConfiguration conversionConfiguration, CustomUserDetailsService customUserDetailsService) {
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

        if (validUserTokens.isEmpty()) return;

        for (Token token: validUserTokens) {
            token.setExpired(true);
            token.setRevoked(true);
        }

        tokenRepository.saveAll(validUserTokens);
    }

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

    public AuthenticationDetailDto authenticate(LoginDto loginDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getIdentifier(), loginDto.getPassword()));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginDto.getIdentifier());

        String jwtToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        revokeAllUserTokens(user.getId());
        saveUserToken(user, jwtToken);

        return AuthenticationDetailDto.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null ||!authHeader.startsWith("Bearer ")) return;


        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        if (!jwtService.isTokenValid(refreshToken, userDetails)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
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
