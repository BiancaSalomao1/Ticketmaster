package com.globaltech.ticketmaster.order.infrastructure.rest;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record OrderRequest(
        @NotNull(message = "Ticket ID is required")
        UUID ticketId
) {}