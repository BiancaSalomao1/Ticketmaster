package com.globaltech.ticketmaster.order.infrastructure.config;

import com.globaltech.ticketmaster.order.application.usecase.CreateOrderUseCaseImpl;
import com.globaltech.ticketmaster.order.domain.ports.CreateOrderUseCase;
import com.globaltech.ticketmaster.order.domain.ports.OrderMessagePublisherPort;
import com.globaltech.ticketmaster.order.domain.ports.OrderRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public CreateOrderUseCase createOrderUseCase(
            OrderMessagePublisherPort messagePublisherPort,
            OrderRepositoryPort orderRepositoryPort
    ) {
        return new CreateOrderUseCaseImpl(messagePublisherPort, orderRepositoryPort);
    }
}