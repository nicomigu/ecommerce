package com.nicomigu.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicomigu.auth_service.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}