package com.globaltech.ticketmaster.order.infrastructure;

import com.globaltech.ticketmaster.order.domain.ports.OrderMessagePublisherPort;
import com.globaltech.ticketmaster.order.domain.ports.OrderRepositoryPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

    @MockBean
    protected OrderMessagePublisherPort messagePublisherPort;

    @MockBean
    protected OrderRepositoryPort orderRepositoryPort;
}