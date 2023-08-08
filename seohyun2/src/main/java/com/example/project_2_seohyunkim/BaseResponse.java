package com.example.project_2_seohyunkim;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonPropertyOrder({"message"})
@ApiModel(value = "기본 응답")
public class BaseResponse<T> {

    @Schema(description = "응답 메시지", required = true, example = "성공")
    private final String message;

    public BaseResponse(ResponseMessage responseMessage) {
        this.message = responseMessage.getMessage();
    }
}
