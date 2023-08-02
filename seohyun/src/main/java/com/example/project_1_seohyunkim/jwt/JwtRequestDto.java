package com.example.project_1_seohyunkim.jwt;

import lombok.Data;

@Data
public class JwtRequestDto {
    private String username;
    private String password;
}