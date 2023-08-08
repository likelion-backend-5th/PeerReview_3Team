package com.example.project_1_seohyunkim.mapping;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "suggestedPrice", "status"}) //GET 출력 순서설정
public interface NegotiationsMapping {
    Long getId();
    Integer getSuggestedPrice();
    String getStatus();
}
