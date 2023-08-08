package com.example.mutsasns.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "users")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;

    private String profileImg;

    private String email;

    private String phone;
}
