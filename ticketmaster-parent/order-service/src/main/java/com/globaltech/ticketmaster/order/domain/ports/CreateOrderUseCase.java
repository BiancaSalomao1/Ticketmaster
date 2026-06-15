package com.globaltech.ticketmaster.order.domain.ports;

import java.util.UUID;

public interface CreateOrderUseCase {
      void execute(UUID orderId, UUID customerId, UUID ticketId);
}