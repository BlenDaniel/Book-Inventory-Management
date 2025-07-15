package com.book.inventory.management.bookim.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.book.inventory.management.bookim.model.User;
import com.book.inventory.management.bookim.repository.UsersRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

        private UsersRepository usersRepository;

        @Override
        public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

                User user = usersRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "User does not exists by Username or Email"));

                Set<GrantedAuthority> authorities = user.getKeys().getRoles()
                                .stream()
                                .map((role) -> new SimpleGrantedAuthority(role.getName()))
                                .collect(Collectors.toSet());

                return new org.springframework.security.core.userdetails.User(
                                usernameOrEmail,
                                user.getPassword(),
                                authorities);

        }

}
