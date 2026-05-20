package com.helpdesk.api.controller;

import com.helpdesk.api.dto.request.CreatePublicCommentRequest;
import com.helpdesk.api.dto.request.CreateSupportCommentRequest;
import com.helpdesk.api.dto.response.CommentResponse;
import com.helpdesk.api.model.entry.Profile;
import com.helpdesk.api.model.ticket.Ticket;
import com.helpdesk.api.repository.entry.ProfileRepository;
import com.helpdesk.api.repository.ticket.TicketRepository;
import com.helpdesk.api.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final TicketRepository ticketRepository;
    private final ProfileRepository profileRepository;

    public CommentController(CommentService commentService, TicketRepository ticketRepository, ProfileRepository profileRepository) {
        this.commentService = commentService;
        this.ticketRepository = ticketRepository;
        this.profileRepository = profileRepository;
    }

    @PostMapping("/public")
    public ResponseEntity<CommentResponse> addPublicComment(@RequestBody CreatePublicCommentRequest req) {
        try {
            Ticket ticket = ticketRepository.findById(req.ticketId).orElseThrow(() -> new IllegalArgumentException("Ticket not found"));
            Profile author = profileRepository.findById(req.authorId).orElseThrow(() -> new IllegalArgumentException("Author not found"));
            return new ResponseEntity<>(CommentResponse.from(commentService.addComment(ticket, req.text, author)), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/support")
    public ResponseEntity<CommentResponse> addSupportComment(@RequestBody CreateSupportCommentRequest req) {
        try {
            Ticket ticket = ticketRepository.findById(req.ticketId).orElseThrow(() -> new IllegalArgumentException("Ticket not found"));
            Profile author = profileRepository.findById(req.authorId).orElseThrow(() -> new IllegalArgumentException("Author not found"));
            return new ResponseEntity<>(CommentResponse.from(commentService.addSupportComment(ticket, req.text, req.isVisibleToUser, author)), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

