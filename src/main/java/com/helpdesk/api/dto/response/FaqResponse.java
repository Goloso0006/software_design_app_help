package com.helpdesk.api.dto.response;

import com.helpdesk.api.model.ticket.TicketFaq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FaqResponse {
    private String id;
    private String ticketId;
    private String question;
    private String answer;
    private String selectedCommentId;

    public static FaqResponse from(TicketFaq faq) {
        if (faq == null) {
            return null;
        }
        return new FaqResponse(
                faq.getId(),
                faq.getTicket() != null ? faq.getTicket().getId() : null,
                faq.getQuestion(),
                faq.getAnswer(),
                faq.getSelectedComment() != null ? faq.getSelectedComment().getId() : null
        );
    }
}

