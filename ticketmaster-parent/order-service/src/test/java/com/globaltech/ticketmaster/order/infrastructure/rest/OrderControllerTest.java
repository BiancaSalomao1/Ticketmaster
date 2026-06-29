package com.globaltech.ticketmaster.order.infrastructure.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globaltech.ticketmaster.order.infrastructure.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@AutoConfigureMockMvc
class OrderControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should create order with success and return 202 Accepted when request is authenticated")
    void shouldCreateOrderWithSuccess() throws Exception {
        UUID ticketId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        OrderRequest request = new OrderRequest(ticketId);

        mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(jwt()
                                .jwt(builder -> builder.subject(customerId.toString()))
                                .authorities(() -> "SCOPE_tickets:write")))
                .andExpect(status().isAccepted())
                .andExpect(header().exists("Location"));

        Mockito.verify(orderRepositoryPort, Mockito.times(1)).save(any());
        Mockito.verify(messagePublisherPort, Mockito.times(1)).publishOrderIntent(any());
    }

    @Test
    @DisplayName("Should return 403 Forbidden when JWT is missing required scope")
    void shouldReturnForbiddenWhenMissingScope() throws Exception {
        OrderRequest request = new OrderRequest(UUID.randomUUID());

        mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(jwt().authorities(() -> "SCOPE_invalid:scope")))
                .andExpect(status().isForbidden());

        Mockito.verifyNoInteractions(orderRepositoryPort);
        Mockito.verifyNoInteractions(messagePublisherPort);
    }
}