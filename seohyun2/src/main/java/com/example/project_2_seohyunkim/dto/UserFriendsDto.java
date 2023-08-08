package com.example.project_2_seohyunkim.dto;

import com.example.project_2_seohyunkim.entity.UserFollowsEntity;
import com.example.project_2_seohyunkim.entity.UserFriendsEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserFriendsDto {
    private Long id;

    public static UserFriendsDto fromEntity(UserFriendsEntity entity) {
        UserFriendsDto userFriendsDto = new UserFriendsDto();
        userFriendsDto.setId(entity.getId());
        return userFriendsDto;
    }
}
