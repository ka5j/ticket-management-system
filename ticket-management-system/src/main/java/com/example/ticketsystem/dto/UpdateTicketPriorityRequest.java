package com.example.ticketsystem.dto;

import com.example.ticketsystem.model.TicketPriority;
import jakarta.validation.constraints.NotNull;

public class UpdateTicketPriorityRequest {

    @NotNull(message = "Priority is required")
    private TicketPriority priority;

    public TicketPriority getPriority() { return priority; }
    public void setPriority(TicketPriority priority) { this.priority = priority; }
}
