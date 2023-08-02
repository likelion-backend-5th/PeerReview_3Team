package com.example.project_1_seohyunkim.mapping;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "title", "description", "minPriceWanted", "imageUrl", "status"}) //PUT 출력 순서설정
public interface ItemsMapping {
    Long getId();
    String getTitle();
    String getDescription();
    Integer getMinPriceWanted();
    String getImageUrl();
    String getStatus();
}
