package com.example.mutsamarket.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Entity
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private SalesItemEntity salesItem;

    @NotNull
    private Long itemId;

    @NotNull
    private String writer;

    private String password;

    @NotNull
    private String content;

    private String reply;
}
