package com.example.mutsamarket.dto;

import com.example.mutsamarket.entity.CommentEntity;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private Long itemId;
    private String writer;
    private String password;
    private String content;
    private String reply;

    public static CommentDto fromEntity(CommentEntity entity) {
        CommentDto dto = new CommentDto();
        dto.setId(entity.getId());
        dto.setItemId(entity.getItemId());
        dto.setWriter(entity.getWriter());
        dto.setPassword(entity.getPassword());
        dto.setContent(entity.getContent());
        dto.setReply(entity.getReply());
        return dto;
    }

    public CommentEntity newEntity() {
        CommentEntity entity = new CommentEntity();
        entity.setId(id);
        entity.setItemId(itemId);
        entity.setWriter(writer);
        entity.setPassword(password);
        entity.setContent(content);
        entity.setReply(reply);
        return entity;
    }


}
