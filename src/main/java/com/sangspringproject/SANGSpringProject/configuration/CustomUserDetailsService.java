package com.sangspringproject.SANGSpringProject.configuration;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sangspringproject.SANGSpringProject.models.User;
import com.sangspringproject.SANGSpringProject.services.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final userService userService;

    public CustomUserDetailsService(userService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User with email " + email + " not found.");
        }

        String role = user.getRole().equals("ROLE_ADMIN") ? "ADMIN" : "USER";

        return org.springframework.security.core.userdetails.User
                .withUsername(email)
                .password(user.getPassword())
                .roles(role)
                .build();
    }
}
