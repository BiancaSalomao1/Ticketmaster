package com.globaltech.ticketmaster.order.infrastructure.rest;

import com.globaltech.ticketmaster.order.domain.ports.CreateOrderUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('SCOPE_tickets:write')")
    public ResponseEntity<Void> createOrder(
            @Valid @RequestBody OrderRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String customerIdFromToken = jwt.getSubject();
        UUID orderId = UUID.randomUUID();

        createOrderUseCase.execute(orderId, UUID.fromString(customerIdFromToken), request.ticketId());

        URI location = URI.create("/v1/orders/" + orderId);
        return ResponseEntity.accepted().location(location).build();
    }
}