package com.nicomigu.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicomigu.auth_service.model.Role;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Boolean existsByName(String name);

    Optional<Role> findByName(String name);

}