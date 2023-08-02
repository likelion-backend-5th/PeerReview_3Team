package com.example.mutsamarket.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Entity
@Table(name = "salesItem")
public class SalesItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    private String image_url;

    @NotNull
    private Long minPriceWanted;

    private String status;

    @NotNull
    private String writer;

    private String password;

}