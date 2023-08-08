package com.example.project_2_seohyunkim.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String username;
    private String password;
    private String phone;
    private String email;
    private String profile;
}
