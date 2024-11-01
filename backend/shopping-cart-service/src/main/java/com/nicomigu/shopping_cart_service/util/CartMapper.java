package com.nicomigu.shopping_cart_service.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nicomigu.shopping_cart_service.dto.CartItemRequest;
import com.nicomigu.shopping_cart_service.dto.CartItemResponse;
import com.nicomigu.shopping_cart_service.dto.CartResponse;
import com.nicomigu.shopping_cart_service.model.ShoppingCart;
import com.nicomigu.shopping_cart_service.model.ShoppingCartItem;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "total", expression = "java(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))")
    CartItemResponse toResponse(ShoppingCartItem item);

    @Mapping(source = "items", target = "items")
    @Mapping(target = "total", expression = "java(cart.getTotal())")
    CartResponse toResponse(ShoppingCart cart);

    List<CartItemResponse> toCartItemResponseList(List<ShoppingCartItem> items);

    // Map CartItemRequest to ShoppingCartItem for add/update operations
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cart", ignore = true)
    ShoppingCartItem toShoppingCartItem(CartItemRequest request);
}
