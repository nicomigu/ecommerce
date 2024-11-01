package com.nicomigu.shopping_cart_service.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nicomigu.shopping_cart_service.model.ShoppingCart;
import com.nicomigu.shopping_cart_service.repository.ShoppingCartRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class CartCleanupScheduler {
    private final ShoppingCartRepository cartRepository;

    @Value("${cart.expiration.hours:24}")
    private int expirationHours;

    @Scheduled(cron = "0 0 */1 * * *") // Run every hour
    public void cleanupExpiredCarts() {
        LocalDateTime expirationTime = LocalDateTime.now().minusHours(expirationHours);
        List<ShoppingCart> expiredCarts = cartRepository.findByLastModifiedBefore(expirationTime);

        log.info("Found {} expired carts to clean up", expiredCarts.size());
        cartRepository.deleteAll(expiredCarts);
    }
}