package com.travellog.travellog.services;

import com.travellog.travellog.models.Token;
import com.travellog.travellog.repositories.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class LogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;

    @Autowired
    public LogoutService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (authHeader == null ||!authHeader.startsWith("Bearer")) return;

        jwt = authHeader.substring(7);
        Token storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);

        if (storedToken == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        storedToken.setExpired(true);
        storedToken.setRevoked(true);
        tokenRepository.save(storedToken);
        SecurityContextHolder.clearContext();
    }
}
