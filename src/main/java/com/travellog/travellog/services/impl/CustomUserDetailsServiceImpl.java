package com.travellog.travellog.services.impl;

import com.travellog.travellog.models.User;
import com.travellog.travellog.models.UserPrincipal;
import com.travellog.travellog.repositories.UserRepository;
import com.travellog.travellog.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        Optional<User> userFoundByUsername = userRepository.findByUsername(identifier);
        Optional<User> userFoundByEmail = userRepository.findByEmail(identifier);

        User foundUser = userFoundByUsername.orElseGet(userFoundByEmail::get);

        return UserPrincipal.create(foundUser);
    }
}
