package com.helpdesk.api.controller;

import com.helpdesk.api.dto.request.PublishFaqRequest;
import com.helpdesk.api.dto.response.FaqResponse;
import com.helpdesk.api.model.entry.Profile;
import com.helpdesk.api.model.commentary.SupportComment;
import com.helpdesk.api.model.ticket.Ticket;
import com.helpdesk.api.repository.entry.ProfileRepository;
import com.helpdesk.api.repository.commentary.SupportCommentRepository;
import com.helpdesk.api.repository.ticket.TicketRepository;
import com.helpdesk.api.service.FAQService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/faqs")
public class FAQController {

    private final FAQService faqService;
    private final ProfileRepository profileRepository;
    private final TicketRepository ticketRepository;
    private final SupportCommentRepository supportCommentRepository;

    public FAQController(FAQService faqService, ProfileRepository profileRepository, TicketRepository ticketRepository, SupportCommentRepository supportCommentRepository) {
        this.faqService = faqService;
        this.profileRepository = profileRepository;
        this.ticketRepository = ticketRepository;
        this.supportCommentRepository = supportCommentRepository;
    }

    @PostMapping("/publish")
    public ResponseEntity<FaqResponse> publishToFaq(@RequestBody PublishFaqRequest req) {
        try {
            Profile admin = profileRepository.findById(req.adminId).orElseThrow(() -> new IllegalArgumentException("Admin profile not found"));
            Ticket ticket = ticketRepository.findById(req.ticketId).orElseThrow(() -> new IllegalArgumentException("Ticket not found"));
            SupportComment comment = supportCommentRepository.findById(req.supportCommentId).orElseThrow(() -> new IllegalArgumentException("Support comment not found"));

            return new ResponseEntity<>(FaqResponse.from(faqService.publishToFAQ(admin, ticket, comment)), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

