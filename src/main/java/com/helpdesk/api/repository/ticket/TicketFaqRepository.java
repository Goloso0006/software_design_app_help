package com.helpdesk.api.repository.ticket;

import com.helpdesk.api.model.ticket.Ticket;
import com.helpdesk.api.model.ticket.TicketFaq;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketFaqRepository extends MongoRepository<TicketFaq, String> {

    Optional<TicketFaq> findByTicket(Ticket ticket);

    boolean existsByTicket(Ticket ticket);
}

