package com.travellog.travellog.services.spec;

import com.travellog.travellog.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface ICustomUserDetailsService extends UserDetailsService {
    UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException;

    User getAuthenticatedUser();
}
