package com.example.tic.service;

import com.example.tic.entity.User;
import com.example.tic.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repo;

    public UserDetailsServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        User u = (identifier != null && identifier.contains("@"))
                ? repo.findByEmail(identifier).orElseThrow(() -> new UsernameNotFoundException("User not found"))
                : repo.findByNationalId(identifier).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String role = (u.getRole() == null || u.getRole().isBlank()) ? "USER" : u.getRole();
        boolean enabled = u.getIsActive() != null && u.getIsActive() == 1;

        return new org.springframework.security.core.userdetails.User(
                u.getEmail(),
                u.getPassword(),
                enabled,
                true, true, true,
                List.of(new SimpleGrantedAuthority("ROLE_" + role))
        );
    }
}
