package com.globaltech.ticketmaster.order.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Order {
    private final UUID id;
    private final UUID customerId;
    private final UUID ticketId;
    private final BigDecimal totalAmount;
    private OrderStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    public Order(UUID id, UUID customerId, UUID ticketId, BigDecimal totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.ticketId = ticketId;
        this.totalAmount = totalAmount;
        this.status = OrderStatus.PENDING_PAYMENT;
        this.createdAt = LocalDateTime.now();
        this.expiresAt = this.createdAt.plusMinutes(10); // O cliente tem 10 minutos para pagar
    }

    public Order(UUID id, UUID customerId, UUID ticketId, BigDecimal totalAmount,
                 OrderStatus status, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.id = id;
        this.customerId = customerId;
        this.ticketId = ticketId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public UUID getId() { return id; }
    public UUID getCustomerId() { return customerId; }
    public UUID getTicketId() { return ticketId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public OrderStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getExpiresAt() { return expiresAt; }


    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiresAt) && this.status == OrderStatus.PENDING_PAYMENT;
    }

    public void markAsPaid() {
        if (this.status != OrderStatus.PENDING_PAYMENT) {
            throw new IllegalStateException("Order cannot be paid from current status: " + this.status);
        }
        this.status = OrderStatus.PAID;
    }

    public void cancel() {
        if (this.status == OrderStatus.PAID) {
            throw new IllegalStateException("A paid order cannot be cancelled without a refund flow.");
        }
        this.status = OrderStatus.CANCELLED;
    }
}