package com.nicomigu.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicomigu.auth_service.model.Authority;
import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> findByName(String name);

    Boolean existsByName(String name);
}