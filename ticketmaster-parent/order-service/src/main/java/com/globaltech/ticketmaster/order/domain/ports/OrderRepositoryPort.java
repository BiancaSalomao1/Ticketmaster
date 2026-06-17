package com.globaltech.ticketmaster.order.domain.ports;

import com.globaltech.ticketmaster.order.domain.model.Order;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepositoryPort {
    void save(Order order);
    Optional<Order> findById(UUID id);
}