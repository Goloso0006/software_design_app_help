package com.helpdesk.api.dto.response;

import com.helpdesk.api.model.commentary.Comment;
import com.helpdesk.api.model.commentary.PublicComment;
import com.helpdesk.api.model.commentary.SupportComment;
import com.helpdesk.api.model.enums.commentary.CommentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentResponse {
    private String id;
    private String ticketId;
    private String authorId;
    private String description;
    private LocalDateTime createDate;
    private String type;
    private Boolean visibleToUser;
    private Integer likes;
    private Boolean reported;
    private CommentStatus status;

    public static CommentResponse from(Comment comment) {
        if (comment == null) {
            return null;
        }

        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setTicketId(comment.getTicket() != null ? comment.getTicket().getId() : null);
        response.setAuthorId(comment.getAuthor() != null ? comment.getAuthor().getId() : null);
        response.setDescription(comment.getDescription());
        response.setCreateDate(comment.getCreateDate());
        response.setType(comment.getClass().getSimpleName());

        if (comment instanceof SupportComment supportComment) {
            response.setVisibleToUser(supportComment.isVisibleToUser());
        }
        if (comment instanceof PublicComment publicComment) {
            response.setLikes(publicComment.getLikes());
            response.setReported(publicComment.isReported());
            response.setStatus(publicComment.getStatus());
        }

        return response;
    }
}

