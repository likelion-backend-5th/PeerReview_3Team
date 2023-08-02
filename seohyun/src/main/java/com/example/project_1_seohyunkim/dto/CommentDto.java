package com.example.project_1_seohyunkim.dto;

import com.example.project_1_seohyunkim.entity.CommentEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto {
    private Long id;
//    private Long itemId;

//    @NotBlank(message = "writer is required")
//    @Size(min = 2, message = "at least 2 characters long")
//    private String writer;
//
//    @Size(min = 8, message = "at least 8 characters long")
//    private String password;

    @NotBlank(message = "content is required")
    private String content;

    private String reply;

    public static CommentDto fromEntity(CommentEntity commentEntity) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(commentEntity.getId());
//        commentDto.setItemId(commentEntity.getItemId());
//        commentDto.setWriter(commentEntity.getWriter());
//        commentDto.setPassword(commentEntity.getPassword());
        commentDto.setContent(commentEntity.getContent());
        commentDto.setReply(commentEntity.getReply());
        return commentDto;
    }
}
