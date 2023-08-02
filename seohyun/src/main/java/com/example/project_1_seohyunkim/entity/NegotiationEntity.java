package com.example.project_1_seohyunkim.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@DynamicInsert
@DynamicUpdate
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "negotiation")
public class NegotiationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "negotiation_id")
    private Long id;

//    @Column(name = "item_id", nullable = false)
//    private Long itemId;

    @Column(name = "suggested_price")
    private Integer suggestedPrice;

    @Column
    @ColumnDefault("제안")
    private String status;

//    @Column
//    private String writer;
//
//    @Column
//    private String password;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private SalesItemEntity item;

}
