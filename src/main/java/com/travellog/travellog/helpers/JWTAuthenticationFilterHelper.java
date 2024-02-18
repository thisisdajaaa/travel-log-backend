package com.travellog.travellog.helpers;

import com.travellog.travellog.repositories.ITokenRepository;
import com.travellog.travellog.services.spec.IJWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JWTAuthenticationFilterHelper extends OncePerRequestFilter {

    private final IJWTService jwtService;
    private final UserDetailsService userDetailsService;
    private final ITokenRepository tokenRepository;

    public JWTAuthenticationFilterHelper(IJWTService jwtService, UserDetailsService userDetailsService,
            ITokenRepository tokenRepository) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.tokenRepository = tokenRepository;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt = authHeader.substring(7);

        String userEmail = "";

        try {
            userEmail = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            ResponseHelper.respondWithUnauthorizedError(response, "JWT token is not valid.");
            return;
        }

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (!jwtService.isTokenValid(jwt, userDetails)) {
                ResponseHelper.respondWithUnauthorizedError(response, "JWT token is not valid.");
                return;
            }

            Boolean isTokenValid = tokenRepository.findByToken(jwt)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);

            if (!isTokenValid) {
                ResponseHelper.respondWithUnauthorizedError(response, "JWT token is expired or revoked.");
                return;
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}
