package com.nicomigu.auth_service.dto;

public record AuthenticationRequest(
        String email,
        String password) {
}