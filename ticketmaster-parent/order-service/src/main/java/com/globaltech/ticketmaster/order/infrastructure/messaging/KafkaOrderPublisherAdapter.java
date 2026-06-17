package com.globaltech.ticketmaster.order.infrastructure.messaging;

import com.globaltech.ticketmaster.order.domain.model.Order;
import com.globaltech.ticketmaster.order.domain.ports.OrderMessagePublisherPort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaOrderPublisherAdapter implements OrderMessagePublisherPort {

    private static final String TOPIC_ORDER_INTENT = "tickets.order.intent";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaOrderPublisherAdapter(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishOrderIntent(Order order) {
        OrderEvent event = new OrderEvent(
                order.getId(),
                order.getCustomerId(),
                order.getTicketId(),
                order.getTotalAmount(),
                order.getStatus().name(),
                order.getCreatedAt().toString(),
                order.getExpiresAt().toString()
        );

        kafkaTemplate.send(TOPIC_ORDER_INTENT, order.getTicketId().toString(), event);
    }
}