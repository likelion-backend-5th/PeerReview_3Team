package com.example.project_2_seohyunkim.mapping;

import com.example.project_2_seohyunkim.entity.ArticleEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "userId", "title", "content", "imageUrl"})
public interface ArticlesMapping {
    Long getId();
    Long getUserId();
    String getTitle();
    String getContent();
    String getImageUrl();
}
