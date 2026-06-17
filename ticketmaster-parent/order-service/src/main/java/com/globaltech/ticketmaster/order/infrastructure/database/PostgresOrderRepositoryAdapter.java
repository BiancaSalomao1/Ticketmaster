package com.globaltech.ticketmaster.order.infrastructure.database;

import com.globaltech.ticketmaster.order.domain.model.Order;
import com.globaltech.ticketmaster.order.domain.ports.OrderRepositoryPort;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.UUID;

@Component
public class PostgresOrderRepositoryAdapter implements OrderRepositoryPort {

    private final SpringDataOrderRepository repository;

    public PostgresOrderRepositoryAdapter(SpringDataOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Order order) {
        OrderEntity entity = new OrderEntity(order);
        repository.save(entity);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return repository.findById(id).map(OrderEntity::toDomain);
    }
}