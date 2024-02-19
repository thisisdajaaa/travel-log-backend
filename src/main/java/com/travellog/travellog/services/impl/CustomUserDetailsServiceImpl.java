package com.travellog.travellog.services.impl;

import com.travellog.travellog.exceptions.UserException;
import com.travellog.travellog.models.User;
import com.travellog.travellog.models.UserPrincipal;
import com.travellog.travellog.repositories.IUserRepository;
import com.travellog.travellog.services.spec.ICustomUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsServiceImpl implements ICustomUserDetailsService {
    private final IUserRepository userRepository;

    public CustomUserDetailsServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        Optional<User> userFoundByUsername = userRepository.findByUsername(identifier);
        Optional<User> userFoundByEmail = userRepository.findByEmail(identifier);

        User foundUser = userFoundByUsername.orElseGet(userFoundByEmail::get);

        return UserPrincipal.create(foundUser);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            throw new UserException.NotFoundException("No authenticated user found.");
        }

        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UserException.NotFoundException(("Authenticated user not found in database.")));
    }
}
