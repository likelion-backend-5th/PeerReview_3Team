package com.likelion.MutsaMarket.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Entity
@Table(name = "sales_item")
public class SalesItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    private String imageURL;

    @NotNull
    private Long minPriceWanted;

    private String status;

    @NotNull
    private String writer;

    private String password;
}
