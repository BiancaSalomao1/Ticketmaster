package com.globaltech.ticketmaster.order.application.usecase;

import com.globaltech.ticketmaster.order.domain.model.Order;
import com.globaltech.ticketmaster.order.domain.ports.CreateOrderUseCase;
import com.globaltech.ticketmaster.order.domain.ports.OrderMessagePublisherPort;
import com.globaltech.ticketmaster.order.domain.ports.OrderRepositoryPort;
import java.math.BigDecimal;
import java.util.UUID;

public class CreateOrderUseCaseImpl implements CreateOrderUseCase {

    private final OrderMessagePublisherPort messagePublisherPort;
    private final OrderRepositoryPort orderRepositoryPort;

    public CreateOrderUseCaseImpl(OrderMessagePublisherPort messagePublisherPort, OrderRepositoryPort orderRepositoryPort) {
        this.messagePublisherPort = messagePublisherPort;
        this.orderRepositoryPort = orderRepositoryPort;
    }

    @Override
    public void execute(UUID orderId, UUID customerId, UUID ticketId) {
        Order order = new Order(orderId, customerId, ticketId, new BigDecimal("150.00"));

        orderRepositoryPort.save(order);
        messagePublisherPort.publishOrderIntent(order);
    }
}