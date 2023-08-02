package com.example.project_1_seohyunkim.mapping;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ItemInfoMapping {
    String getTitle();
    String getDescription();
    Integer getMinPriceWanted();
    String getImageUrl();
    String getStatus();
}
