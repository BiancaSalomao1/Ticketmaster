package com.globaltech.ticketmaster.order.domain.ports;

import com.globaltech.ticketmaster.order.domain.model.Order;

public interface OrderMessagePublisherPort {
    void publishOrderIntent(Order order);
}