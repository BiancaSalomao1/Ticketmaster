package com.globaltech.ticketmaster.order.infrastructure.messaging;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderEvent(
        UUID orderId,
        UUID customerId,
        UUID ticketId,
        BigDecimal totalAmount,
        String status,
        String createdAt,
        String expiresAt
) {}