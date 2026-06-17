package com.globaltech.ticketmaster.order.infrastructure.database;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringDataOrderRepository extends JpaRepository<OrderEntity, UUID> {
}