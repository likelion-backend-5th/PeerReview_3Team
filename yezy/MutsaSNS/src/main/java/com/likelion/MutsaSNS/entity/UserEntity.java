package com.likelion.MutsaSNS.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // DB 제약사항 추가
    @Column(nullable = false, unique = true)
    private String username;
    @NotBlank
    private String password;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "user")
    private List<CommentEntity> comment;

    @OneToMany(mappedBy = "user")
    private List<SalesItemEntity> items;
}
