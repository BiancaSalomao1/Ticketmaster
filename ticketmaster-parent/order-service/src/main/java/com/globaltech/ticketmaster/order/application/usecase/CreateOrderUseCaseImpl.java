package com.globaltech.ticketmaster.order.application.usecase;

import com.globaltech.ticketmaster.order.domain.model.Order;
import com.globaltech.ticketmaster.order.domain.ports.CreateOrderUseCase;
import com.globaltech.ticketmaster.order.domain.ports.OrderMessagePublisherPort;
import java.math.BigDecimal;
import java.util.UUID;

public class CreateOrderUseCaseImpl implements CreateOrderUseCase {

    private final OrderMessagePublisherPort messagePublisherPort;


    public CreateOrderUseCaseImpl(OrderMessagePublisherPort messagePublisherPort) {
        this.messagePublisherPort = messagePublisherPort;
    }

    @Override
    public void execute(UUID orderId, UUID customerId, UUID ticketId) {
        Order order = new Order(orderId, customerId, ticketId, new BigDecimal("150.00"));

        messagePublisherPort.publishOrderIntent(order);
    }
}