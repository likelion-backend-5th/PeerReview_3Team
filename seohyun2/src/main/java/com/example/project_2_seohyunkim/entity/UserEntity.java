package com.example.project_2_seohyunkim.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "profile_img")
    private String profile;

    @Column
    private String email;

    @Column
    private String phone;

    @OneToMany(mappedBy = "user")
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ArticleEntity> articles = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<LikeArticleEntity> likeArticles = new ArrayList<>();

    @OneToMany(mappedBy = "follower")
    private List<UserFollowsEntity> followers = new ArrayList<>();

    @OneToMany(mappedBy = "following")
    private List<UserFollowsEntity> followings = new ArrayList<>();

    @OneToMany(mappedBy = "fromUser")
    private List<UserFriendsEntity> fromUsers = new ArrayList<>();

    @OneToMany(mappedBy = "toUser")
    private List<UserFriendsEntity> toUsers = new ArrayList<>();
}
