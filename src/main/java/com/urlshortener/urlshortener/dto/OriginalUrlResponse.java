package com.urlshortener.urlshortener.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OriginalUrlResponse {
    private String originalUrl;
} 