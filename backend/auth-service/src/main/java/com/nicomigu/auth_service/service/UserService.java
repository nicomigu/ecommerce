package com.nicomigu.auth_service.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nicomigu.auth_service.dto.UserRoleRequest;
import com.nicomigu.auth_service.dto.UserRoleResponse;
import com.nicomigu.auth_service.model.Role;
import com.nicomigu.auth_service.model.User;
import com.nicomigu.auth_service.repository.RoleRepository;
import com.nicomigu.auth_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }

    private User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    private Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName).orElseThrow(() -> new UsernameNotFoundException("role not found"));
    }

    public UserRoleResponse assignRoleToUser(UserRoleRequest request) {
        User user = getUserById(request.id());
        Role role = getRoleByName(request.roleName());

        user.getRoles().add(role);

        user = userRepository.save(user);

        return new UserRoleResponse(user.getId(), user.getUsername(),
                user.getRoles().stream().map(roleEntity -> roleEntity.getName()).collect(Collectors.toSet()));
    }

    // todo add method to get all roles and auth of a user
    public Set<String> getAllRolesAndAuthorities(Long userId) {
        User user = getUserById(userId);

        return user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }

}