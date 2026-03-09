package com.lab.rest.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final AppUserRepository appUserRepository;
    public CustomUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (appUser.getRoles() != null) {
            for (Role role : appUser.getRoles()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
            }
        }

        return new User(
                appUser.getUsername(),
                appUser.getPasswordHash(),
                appUser.isEnabled(),
                true,
                true,
                true,
                authorities
        );
    }
}
