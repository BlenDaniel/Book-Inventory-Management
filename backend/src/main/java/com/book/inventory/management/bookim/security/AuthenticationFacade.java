package com.book.inventory.management.bookim.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthenticationFacade {

    public Authentication getAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Retrieved authentication: {}", auth);
        return auth;
    }

    public String getCurrentUsername() {
        log.info("Getting current email...");
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            log.warn("No authentication found");
            return null;
        }
        String email;
        if (authentication.getPrincipal() instanceof UserDetails) {
            email = ((UserDetails) authentication.getPrincipal()).getUsername();
        } else {
            email = authentication.getName();
        }
        log.info("Current email: {}", email);
        return email;
    }

    public boolean isAuthenticated() {
        log.info("Checking if user is authenticated...");
        Authentication authentication = getAuthentication();
        boolean isAuth = authentication != null &&
                authentication.isAuthenticated() &&
                !"anonymousUser".equals(authentication.getPrincipal());
        log.info("Is authenticated: {}", isAuth);
        return isAuth;
    }

    public UserDetails getCurrentUserDetails() {
        log.info("Getting current user details...");
        Authentication authentication = getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            log.info("User details retrieved: {}", userDetails.getUsername());
            return userDetails;
        }
        log.warn("No user details found");
        return null;
    }
}
