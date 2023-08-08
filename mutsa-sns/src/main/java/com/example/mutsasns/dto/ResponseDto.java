package com.example.mutsasns.dto;

import lombok.Data;

import java.util.HashMap;

@Data
public class ResponseDto {
    private HashMap<String, String> response = new HashMap<>();
}
