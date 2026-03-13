package com.example.ticketsystem.service;

import com.example.ticketsystem.dto.CreateTicketRequest;
import com.example.ticketsystem.dto.TicketResponse;
import com.example.ticketsystem.model.TicketPriority;
import com.example.ticketsystem.model.TicketStatus;

import java.util.List;

public interface TicketService {
    TicketResponse createTicket(CreateTicketRequest request);
    List<TicketResponse> getAllTickets(TicketStatus status, TicketPriority priority);
    TicketResponse getTicketById(Long id);
    TicketResponse updateTicketStatus(Long id, TicketStatus status);
    TicketResponse updateTicketPriority(Long id, TicketPriority priority);
    void deleteTicket(Long id);
}
