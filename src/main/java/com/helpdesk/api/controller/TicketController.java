package com.helpdesk.api.controller;

import com.helpdesk.api.dto.request.CreateTicketRequest;
import com.helpdesk.api.dto.response.TicketResponse;
import com.helpdesk.api.model.entry.Profile;
import com.helpdesk.api.model.ticket.Ticket;
import com.helpdesk.api.model.enums.ticket.TicketCategories;
import com.helpdesk.api.model.enums.ticket.TicketPriorities;
import com.helpdesk.api.repository.entry.ProfileRepository;
import com.helpdesk.api.repository.ticket.TicketRepository;
import com.helpdesk.api.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final ProfileRepository profileRepository;
    private final TicketRepository ticketRepository;

    public TicketController(TicketService ticketService, ProfileRepository profileRepository, TicketRepository ticketRepository) {
        this.ticketService = ticketService;
        this.profileRepository = profileRepository;
        this.ticketRepository = ticketRepository;
    }

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(@RequestBody CreateTicketRequest req) {
        try {
            Profile user = profileRepository.findById(req.userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
            TicketCategories cat = req.category == null ? null : TicketCategories.valueOf(req.category);
            TicketPriorities pri = req.priority == null ? null : TicketPriorities.valueOf(req.priority);
            Ticket created = ticketService.createTicket(user, req.title, req.description, cat, pri);
            return ResponseEntity.ok(TicketResponse.from(created));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getTicket(@PathVariable String id) {
        return ticketRepository.findById(id)
                .map(ticket -> ResponseEntity.ok(TicketResponse.from(ticket)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/my/{userId}")
    public ResponseEntity<List<TicketResponse>> getMyTickets(@PathVariable String userId) {
        try {
            Profile user = profileRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
            List<Ticket> list = ticketService.getMyTickets(user);
            return ResponseEntity.ok(list.stream().map(TicketResponse::from).toList());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

