package com.nicomigu.auth_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nicomigu.auth_service.dto.AuthenticationRequest;
import com.nicomigu.auth_service.dto.AuthenticationResponse;
import com.nicomigu.auth_service.dto.RegisterRequest;
import com.nicomigu.auth_service.model.Role;
import com.nicomigu.auth_service.model.User;
import com.nicomigu.auth_service.repository.RoleRepository;
import com.nicomigu.auth_service.repository.UserRepository;
import com.nicomigu.auth_service.security.JwtService;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        Role role = roleRepository.findByName("ROLE_USER").orElseThrow();

        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles(Set.of(role))
                .build();

        user = userRepository.save(user);
        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.email()));

        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    private boolean isEmail(String login) {
        return login.contains("@");
    }
}
