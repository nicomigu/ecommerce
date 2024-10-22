package com.nicomigu.auth_service.dto;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password) {
}
