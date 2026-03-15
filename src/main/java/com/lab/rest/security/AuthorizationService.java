package com.lab.rest.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component("authz")
public class AuthorizationService {

    public boolean isSelf(Long employeeId, Authentication authentication) {
        if (authentication == null) return false;

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof Jwt jwt)) return false;

        Long empId = jwt.getClaim("empId");
        return empId != null && empId.equals(employeeId);
    }
}