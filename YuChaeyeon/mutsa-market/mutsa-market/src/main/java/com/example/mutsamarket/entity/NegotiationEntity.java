package com.example.mutsamarket.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Entity
@Table(name = "negotiantion")
public class NegotiationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long itemId;

    @NotNull
    private Long suggestedPrice;

    private String status;

    @NotNull
    private String writer;

    private String password;

}
