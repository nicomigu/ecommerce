package com.nicomigu.auth_service.dto;

import java.util.Set;

public record UserRoleResponse(Long id,
        String userName,
        Set<String> roles) {

}
