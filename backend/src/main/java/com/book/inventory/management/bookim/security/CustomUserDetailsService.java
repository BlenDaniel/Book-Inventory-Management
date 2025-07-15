package com.book.inventory.management.bookim.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.book.inventory.management.bookim.model.User;
import com.book.inventory.management.bookim.repository.UsersRepository;

import lombok.AllArgsConstructor;

import java.util.Collections;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

        private UsersRepository usersRepository;

        @Override
        public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
                User user = usersRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "User does not exist by username or email"));

                return new org.springframework.security.core.userdetails.User(
                                usernameOrEmail,
                                user.getPassword(),
                                Collections.emptyList() // No roles or authorities
                );
        }
}
