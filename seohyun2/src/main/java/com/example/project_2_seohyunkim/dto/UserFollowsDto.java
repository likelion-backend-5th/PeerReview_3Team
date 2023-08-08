package com.example.project_2_seohyunkim.dto;

import com.example.project_2_seohyunkim.entity.UserFollowsEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserFollowsDto {
    private Long id;

    public static UserFollowsDto fromEntity(UserFollowsEntity entity) {
        UserFollowsDto userFollowsDto = new UserFollowsDto();
        userFollowsDto.setId(entity.getId());
        return userFollowsDto;
    }
}
