package com.example.ticketsystem.controller;

import com.example.ticketsystem.dto.CreateTicketRequest;
import com.example.ticketsystem.dto.TicketResponse;
import com.example.ticketsystem.dto.UpdateTicketPriorityRequest;
import com.example.ticketsystem.dto.UpdateTicketStatusRequest;
import com.example.ticketsystem.model.TicketPriority;
import com.example.ticketsystem.model.TicketStatus;
import com.example.ticketsystem.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(@Valid @RequestBody CreateTicketRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.createTicket(request));
    }

    @GetMapping
    public ResponseEntity<List<TicketResponse>> getAllTickets(
            @RequestParam(required = false) TicketStatus status,
            @RequestParam(required = false) TicketPriority priority) {
        return ResponseEntity.ok(ticketService.getAllTickets(status, priority));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TicketResponse> updateTicketStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTicketStatusRequest request) {
        return ResponseEntity.ok(ticketService.updateTicketStatus(id, request.getStatus()));
    }

    @PutMapping("/{id}/priority")
    public ResponseEntity<TicketResponse> updateTicketPriority(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTicketPriorityRequest request) {
        return ResponseEntity.ok(ticketService.updateTicketPriority(id, request.getPriority()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}
