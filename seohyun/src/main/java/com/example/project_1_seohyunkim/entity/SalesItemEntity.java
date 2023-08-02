package com.example.project_1_seohyunkim.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@DynamicInsert
@DynamicUpdate //왜 이거써도 update하면 null값 insert?..
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sales_item")
public class SalesItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column(name = "min_price_wanted")
    private Integer minPriceWanted;

    @Column(name = "image_url")
    private String imageUrl;

    @Column
    @ColumnDefault("판매중") // status의 기본값은 "판매중", @DynamicInsert와 함께 쓰여야함
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "item")
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<NegotiationEntity> negotiations = new ArrayList<>();


//    @Column
//    private String writer;
//
//    @Column
//    private String password;
}
