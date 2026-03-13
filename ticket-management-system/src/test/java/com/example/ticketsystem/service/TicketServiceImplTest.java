package com.example.ticketsystem.service;

import com.example.ticketsystem.dto.CreateTicketRequest;
import com.example.ticketsystem.dto.TicketResponse;
import com.example.ticketsystem.exception.ResourceNotFoundException;
import com.example.ticketsystem.model.Ticket;
import com.example.ticketsystem.model.TicketPriority;
import com.example.ticketsystem.model.TicketStatus;
import com.example.ticketsystem.repository.TicketRepository;
import com.example.ticketsystem.service.impl.TicketServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketServiceImpl ticketService;

    private Ticket sampleTicket;

    @BeforeEach
    void setUp() {
        sampleTicket = new Ticket();
        sampleTicket.setId(1L);
        sampleTicket.setTitle("API gateway failure");
        sampleTicket.setDescription("Gateway returns 502 for downstream requests");
        sampleTicket.setStatus(TicketStatus.OPEN);
        sampleTicket.setPriority(TicketPriority.HIGH);
        sampleTicket.setCreatedAt(LocalDateTime.now());
        sampleTicket.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void createTicket_ShouldReturnSavedTicket() {
        CreateTicketRequest request = new CreateTicketRequest();
        request.setTitle("New ticket");
        request.setDescription("Investigate login error");
        request.setPriority(TicketPriority.MEDIUM);

        when(ticketRepository.save(any(Ticket.class))).thenReturn(sampleTicket);

        TicketResponse response = ticketService.createTicket(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("API gateway failure", response.getTitle());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void getTicketById_ShouldReturnTicket_WhenFound() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(sampleTicket));

        TicketResponse response = ticketService.getTicketById(1L);

        assertEquals(TicketStatus.OPEN, response.getStatus());
        verify(ticketRepository).findById(1L);
    }

    @Test
    void getTicketById_ShouldThrow_WhenNotFound() {
        when(ticketRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> ticketService.getTicketById(999L));
    }

    @Test
    void getAllTickets_ShouldReturnAll_WhenNoFiltersProvided() {
        when(ticketRepository.findAll()).thenReturn(List.of(sampleTicket));

        List<TicketResponse> responses = ticketService.getAllTickets(null, null);

        assertEquals(1, responses.size());
        verify(ticketRepository).findAll();
    }

    @Test
    void getAllTickets_ShouldFilterByStatus() {
        when(ticketRepository.findByStatus(TicketStatus.OPEN)).thenReturn(List.of(sampleTicket));

        List<TicketResponse> responses = ticketService.getAllTickets(TicketStatus.OPEN, null);

        assertEquals(1, responses.size());
        verify(ticketRepository).findByStatus(TicketStatus.OPEN);
    }

    @Test
    void getAllTickets_ShouldFilterByPriority() {
        when(ticketRepository.findByPriority(TicketPriority.HIGH)).thenReturn(List.of(sampleTicket));

        List<TicketResponse> responses = ticketService.getAllTickets(null, TicketPriority.HIGH);

        assertEquals(TicketPriority.HIGH, responses.get(0).getPriority());
        verify(ticketRepository).findByPriority(TicketPriority.HIGH);
    }

    @Test
    void getAllTickets_ShouldFilterByStatusAndPriority() {
        when(ticketRepository.findByStatusAndPriority(TicketStatus.OPEN, TicketPriority.HIGH)).thenReturn(List.of(sampleTicket));

        List<TicketResponse> responses = ticketService.getAllTickets(TicketStatus.OPEN, TicketPriority.HIGH);

        assertEquals(1, responses.size());
        verify(ticketRepository).findByStatusAndPriority(TicketStatus.OPEN, TicketPriority.HIGH);
    }

    @Test
    void updateTicketStatus_ShouldPersistNewStatus() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(sampleTicket));
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TicketResponse response = ticketService.updateTicketStatus(1L, TicketStatus.RESOLVED);

        assertEquals(TicketStatus.RESOLVED, response.getStatus());
        verify(ticketRepository).save(any(Ticket.class));
    }

    @Test
    void updateTicketPriority_ShouldPersistNewPriority() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(sampleTicket));
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TicketResponse response = ticketService.updateTicketPriority(1L, TicketPriority.LOW);

        assertEquals(TicketPriority.LOW, response.getPriority());
        verify(ticketRepository).save(any(Ticket.class));
    }

    @Test
    void deleteTicket_ShouldDeleteExistingTicket() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(sampleTicket));

        ticketService.deleteTicket(1L);

        verify(ticketRepository).delete(sampleTicket);
    }

    @Test
    void deleteTicket_ShouldThrow_WhenTicketDoesNotExist() {
        when(ticketRepository.findById(22L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> ticketService.deleteTicket(22L));
        verify(ticketRepository, never()).delete(any(Ticket.class));
    }
}
