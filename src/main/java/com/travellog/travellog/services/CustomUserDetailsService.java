package com.travellog.travellog.services;

import com.travellog.travellog.models.User;
import com.travellog.travellog.models.UserPrincipal;
import com.travellog.travellog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        Optional<User> userFoundByUsername = userRepository.findByUsername(identifier);
        Optional<User> userFoundByEmail = userRepository.findByEmail(identifier);

        User foundUser = userFoundByUsername.orElseGet(userFoundByEmail::get);

        return UserPrincipal.create(foundUser);
    }
}
