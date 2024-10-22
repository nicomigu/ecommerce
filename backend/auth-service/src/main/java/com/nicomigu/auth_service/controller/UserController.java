package com.nicomigu.auth_service.controller;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nicomigu.auth_service.dto.UserRoleRequest;
import com.nicomigu.auth_service.dto.UserRoleResponse;
import com.nicomigu.auth_service.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/assign")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('CREATE_PRIVILEGES')")
    public ResponseEntity<UserRoleResponse> assignRoleToUser(@RequestBody UserRoleRequest request) {
        UserRoleResponse response = userService.assignRoleToUser(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/roles-and-authorities")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Set<String>> getUserRolesAndAuthorities(@PathVariable Long userId) {
        Set<String> rolesAndAuthorities = userService.getAllRolesAndAuthorities(userId);
        return ResponseEntity.ok(rolesAndAuthorities);
    }
}
