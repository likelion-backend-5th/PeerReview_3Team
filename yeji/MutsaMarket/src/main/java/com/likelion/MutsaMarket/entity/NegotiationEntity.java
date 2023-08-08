package com.likelion.MutsaMarket.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.NotFound;

@Data
@Entity
@Table(name = "negotiation")
public class NegotiationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long itemId;

    @NotNull
    private Integer suggestedPrice;

    private String status;

    @NotNull
    private String writer;

    private String password;
}