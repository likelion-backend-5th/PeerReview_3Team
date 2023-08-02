package com.example.project_1_seohyunkim.mapping;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface CommentsMapping {
    Long getId();
    String getContent();
    String getReply();
}
