package com.example.ticketsystem.service.impl;

import com.example.ticketsystem.dto.CreateTicketRequest;
import com.example.ticketsystem.dto.TicketResponse;
import com.example.ticketsystem.exception.ResourceNotFoundException;
import com.example.ticketsystem.model.Ticket;
import com.example.ticketsystem.model.TicketPriority;
import com.example.ticketsystem.model.TicketStatus;
import com.example.ticketsystem.repository.TicketRepository;
import com.example.ticketsystem.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);
    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public TicketResponse createTicket(CreateTicketRequest request) {
        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setPriority(request.getPriority() != null ? request.getPriority() : TicketPriority.MEDIUM);
        ticket.setStatus(TicketStatus.OPEN);

        Ticket savedTicket = ticketRepository.save(ticket);
        logger.info("Created ticket with id={} and priority={}", savedTicket.getId(), savedTicket.getPriority());
        return mapToResponse(savedTicket);
    }

    @Override
    public List<TicketResponse> getAllTickets(TicketStatus status, TicketPriority priority) {
        List<Ticket> tickets;
        if (status != null && priority != null) {
            tickets = ticketRepository.findByStatusAndPriority(status, priority);
        } else if (status != null) {
            tickets = ticketRepository.findByStatus(status);
        } else if (priority != null) {
            tickets = ticketRepository.findByPriority(priority);
        } else {
            tickets = ticketRepository.findAll();
        }
        logger.info("Retrieved {} tickets with filters status={} priority={}", tickets.size(), status, priority);
        return tickets.stream().map(this::mapToResponse).toList();
    }

    @Override
    public TicketResponse getTicketById(Long id) {
        Ticket ticket = getTicketEntity(id);
        logger.info("Retrieved ticket with id={}", id);
        return mapToResponse(ticket);
    }

    @Override
    public TicketResponse updateTicketStatus(Long id, TicketStatus status) {
        Ticket ticket = getTicketEntity(id);
        ticket.setStatus(status);
        Ticket updated = ticketRepository.save(ticket);
        logger.info("Updated ticket id={} status to {}", id, status);
        return mapToResponse(updated);
    }

    @Override
    public TicketResponse updateTicketPriority(Long id, TicketPriority priority) {
        Ticket ticket = getTicketEntity(id);
        ticket.setPriority(priority);
        Ticket updated = ticketRepository.save(ticket);
        logger.info("Updated ticket id={} priority to {}", id, priority);
        return mapToResponse(updated);
    }

    @Override
    public void deleteTicket(Long id) {
        Ticket ticket = getTicketEntity(id);
        ticketRepository.delete(ticket);
        logger.info("Deleted ticket with id={}", id);
    }

    private Ticket getTicketEntity(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));
    }

    private TicketResponse mapToResponse(Ticket ticket) {
        TicketResponse response = new TicketResponse();
        response.setId(ticket.getId());
        response.setTitle(ticket.getTitle());
        response.setDescription(ticket.getDescription());
        response.setStatus(ticket.getStatus());
        response.setPriority(ticket.getPriority());
        response.setCreatedAt(ticket.getCreatedAt());
        response.setUpdatedAt(ticket.getUpdatedAt());
        return response;
    }
}
