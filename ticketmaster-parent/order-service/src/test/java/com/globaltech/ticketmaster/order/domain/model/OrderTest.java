package com.globaltech.ticketmaster.order.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    @DisplayName("Should initialize a new order with pending payment status and 10 minutes expiration")
    void shouldInitializeNewOrderCorrectly() {
        UUID orderId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        UUID ticketId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("150.00");

        Order order = new Order(orderId, customerId, ticketId, amount);

        assertNotNull(order);
        assertEquals(orderId, order.getId());
        assertEquals(customerId, order.getCustomerId());
        assertEquals(ticketId, order.getTicketId());
        assertEquals(amount, order.getTotalAmount());
        assertEquals(OrderStatus.PENDING_PAYMENT, order.getStatus());
        assertNotNull(order.getCreatedAt());
        assertTrue(order.getExpiresAt().isAfter(order.getCreatedAt()));
        assertEquals(order.getCreatedAt().plusMinutes(10), order.getExpiresAt());
    }

    @Test
    @DisplayName("Should mark order as paid when status is pending payment")
    void shouldMarkAsPaidWithSuccess() {
        Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), new BigDecimal("150.00"));

        order.markAsPaid();

        assertEquals(OrderStatus.PAID, order.getStatus());
    }

    @Test
    @DisplayName("Should throw exception when trying to pay an order that is not pending")
    void shouldThrowExceptionWhenPayingNonPendingOrder() {
        Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), new BigDecimal("150.00"));
        order.markAsPaid();

        IllegalStateException exception = assertThrows(IllegalStateException.class, order::markAsPaid);
        assertTrue(exception.getMessage().contains("Order cannot be paid from current status"));
    }

    @Test
    @DisplayName("Should cancel order with success when status is pending payment")
    void shouldCancelOrderWithSuccess() {
        Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), new BigDecimal("150.00"));

        order.cancel();

        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    @DisplayName("Should throw exception when trying to cancel a paid order without refund flow")
    void shouldThrowExceptionWhenCancellingPaidOrder() {
        Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), new BigDecimal("150.00"));
        order.markAsPaid();

        IllegalStateException exception = assertThrows(IllegalStateException.class, order::cancel);
        assertTrue(exception.getMessage().contains("A paid order cannot be cancelled without a refund flow"));
    }

    @Test
    @DisplayName("Should return true when order has passed its expiration time")
    void shouldIdentifyExpiredOrder() {
        UUID id = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        UUID ticketId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("150.00");
        LocalDateTime now = LocalDateTime.now();
        
        Order expiredOrder = new Order(
                id, customerId, ticketId, amount, 
                OrderStatus.PENDING_PAYMENT, 
                now.minusMinutes(15), 
                now.minusMinutes(5)
        );

        assertTrue(expiredOrder.isExpired());
    }
}