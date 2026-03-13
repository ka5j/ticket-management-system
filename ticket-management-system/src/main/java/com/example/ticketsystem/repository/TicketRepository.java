package com.example.ticketsystem.repository;

import com.example.ticketsystem.model.Ticket;
import com.example.ticketsystem.model.TicketPriority;
import com.example.ticketsystem.model.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByStatus(TicketStatus status);
    List<Ticket> findByPriority(TicketPriority priority);
    List<Ticket> findByStatusAndPriority(TicketStatus status, TicketPriority priority);
}
