package com.nicomigu.shopping_cart_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicomigu.shopping_cart_service.model.ShoppingCart;

import java.util.Optional;
import java.util.List;
import java.time.LocalDateTime;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUserId(Long userId);

    List<ShoppingCart> findByLastModifiedBefore(LocalDateTime time);
}
