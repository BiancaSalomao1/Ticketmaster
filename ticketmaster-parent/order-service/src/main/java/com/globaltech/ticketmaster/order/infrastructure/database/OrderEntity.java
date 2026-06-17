package com.globaltech.ticketmaster.order.infrastructure.database;

import com.globaltech.ticketmaster.order.domain.model.Order;
import com.globaltech.ticketmaster.order.domain.model.OrderStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    private UUID id;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "ticket_id", nullable = false)
    private UUID ticketId;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    public OrderEntity() {}

    public OrderEntity(Order order) {
        this.id = order.getId();
        this.customerId = order.getCustomerId();
        this.ticketId = order.getTicketId();
        this.totalAmount = order.getTotalAmount();
        this.status = order.getStatus();
        this.createdAt = order.getCreatedAt();
        this.expiresAt = order.getExpiresAt();
    }

    public Order toDomain() {
        return new Order(
                this.id,
                this.customerId,
                this.ticketId,
                this.totalAmount,
                this.status,
                this.createdAt,
                this.expiresAt
        );
    }
}