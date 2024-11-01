package com.nicomigu.shopping_cart_service.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nicomigu.shopping_cart_service.dto.CartItemRequest;
import com.nicomigu.shopping_cart_service.dto.CartResponse;
import com.nicomigu.shopping_cart_service.exception.CartNotFoundException;
import com.nicomigu.shopping_cart_service.model.ShoppingCart;
import com.nicomigu.shopping_cart_service.model.ShoppingCartItem;
import com.nicomigu.shopping_cart_service.repository.ShoppingCartRepository;
import com.nicomigu.shopping_cart_service.util.CartMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {
    private final ShoppingCartRepository cartRepository;
    private final CartMapper mapper;

    public CartResponse getOrCreateCart(Long userId) {
        ShoppingCart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setUserId(userId);
                    return cartRepository.save(newCart);
                });
        return mapper.toResponse(cart);
    }

    public CartResponse addItem(Long userId, CartItemRequest request) {
        ShoppingCart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setUserId(userId);
                    return cartRepository.save(newCart);
                });

        Optional<ShoppingCartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(request.productId()))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + request.quantity());
        } else {
            ShoppingCartItem newItem = mapper.toShoppingCartItem(request);
            newItem.setCart(cart);
            cart.getItems().add(newItem);
        }

        return mapper.toResponse(cartRepository.save(cart));
    }

    public CartResponse updateItemQuantity(Long userId, Long productId, Integer quantity) {
        ShoppingCart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setUserId(userId);
                    return cartRepository.save(newCart);
                });
        cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));
        return mapper.toResponse(cartRepository.save(cart));
    }

    public CartResponse removeItem(Long userId, Long productId) {
        ShoppingCart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart Not Found"));
        cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        return mapper.toResponse(cartRepository.save(cart));
    }

    public void clearCart(Long userId) {
        ShoppingCart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart Not Found"));

        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
