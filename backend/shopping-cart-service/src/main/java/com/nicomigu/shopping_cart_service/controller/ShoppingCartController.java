package com.nicomigu.shopping_cart_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nicomigu.shopping_cart_service.dto.CartItemRequest;
import com.nicomigu.shopping_cart_service.dto.CartResponse;
import com.nicomigu.shopping_cart_service.dto.UpdateQuantityRequest;
import com.nicomigu.shopping_cart_service.model.ShoppingCart;
import com.nicomigu.shopping_cart_service.service.ShoppingCartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "Shopping Cart", description = "Shopping Cart management API")
public class ShoppingCartController {
    private final ShoppingCartService cartService;

    @Operation(summary = "Get user's cart", description = "Retrieves the current shopping cart for the user or creates a new one if it doesn't exist")
    @ApiResponse(responseCode = "200", description = "Cart retrieved successfully")
    @GetMapping
    public ResponseEntity<CartResponse> getCart(
            @RequestHeader("User-Id") @Parameter(description = "User ID", required = true) Long userId) {
        return ResponseEntity.ok(cartService.getOrCreateCart(userId));
    }

    @Operation(summary = "Add item to cart", description = "Adds a new item to the cart or updates quantity if it already exists")
    @ApiResponse(responseCode = "200", description = "Item added successfully")
    @PostMapping("/items")
    public ResponseEntity<CartResponse> addItem(
            @RequestHeader("User-Id") @Parameter(description = "User ID", required = true) Long userId,
            @Valid @RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.addItem(userId, request));
    }

    @PatchMapping("/items/{productId}")
    @Operation(summary = "Update item quantity in cart", description = "update product quantity in cart")
    @ApiResponse(responseCode = "200", description = "Item updated Successfully")
    public ResponseEntity<CartResponse> updateItemQuantity(
            @RequestHeader("User-Id") Long userId,
            @PathVariable Long productId,
            @RequestBody @Valid UpdateQuantityRequest request) {
        return ResponseEntity.ok(cartService.updateItemQuantity(userId, productId, request.quantity()));
    }

    @DeleteMapping("/items/{productId}")
    @Operation(summary = "delete item in cart", description = "deletes an item in cart")
    @ApiResponse(responseCode = "200", description = "item deleted successfully")
    public ResponseEntity<CartResponse> removeItem(
            @RequestHeader("User-Id") Long userId,
            @PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeItem(userId, productId));
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@RequestHeader("User-Id") Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}