package com.likelion.MutsaMarket.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Entity
@Table(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long itemId;

    @NotNull
    private String writer;

    private String password;

    @NotNull
    private String content;

    private String reply;


    @ManyToOne
    @JoinColumn(name = "itemId", nullable = false)
    private SalesItemEntity salesItem;
}
