package com.likelion.MutsaMarket.dto;

import com.likelion.MutsaMarket.entity.UserEntity;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String passwordCheck;
    private String email;
    private String phone;

    public static UserDto fromEntity(UserEntity entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        return dto;
    }
}
