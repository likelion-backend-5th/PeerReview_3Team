package com.example.mutsasns.dto;

import com.example.mutsasns.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// UserDetails 구현체.
public class UserDto implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private String profileImg;
    private String email;
    private String phone;

    public static UserDto fromEntity(UserEntity entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setProfileImg(entity.getProfileImg());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());

        return dto;
    }

    public UserEntity newEntity() {
        UserEntity entity = new UserEntity();
        entity.setId(id);
        entity.setUsername(username);
        entity.setPassword(password);
        entity.setEmail(email);
        entity.setPhone(phone);

        return entity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

