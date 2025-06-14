package com.urlshortener.urlshortener.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Base64;

@Data
@Document(collection = "url_mappings")
public class UrlMapping {
    
    @Id
    private String shortUrl;
    
    @Indexed(unique = true)
    private String originalUrl;
    private LocalDateTime createdAt;
    private LocalDateTime lastAccessedAt;
    private long accessCount;
    
    public UrlMapping() {
        this.createdAt = LocalDateTime.now();
        this.lastAccessedAt = LocalDateTime.now();
        this.accessCount = 0;
        this.shortUrl = generateShortUrl();
    }

    public UrlMapping(String originalUrl) {
        this();
        this.originalUrl = originalUrl;
    }

    private String generateShortUrl() {
        String uuid = UUID.randomUUID().toString();
        return Base64.getUrlEncoder()
                .encodeToString(uuid.getBytes())
                .substring(0, 8)
                .replaceAll("[^a-zA-Z0-9]", "x"); // Replace any non-alphanumeric chars with 'x'
    }
} 