package com.likelion.MutsaSNS.dto;

import com.likelion.MutsaSNS.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
