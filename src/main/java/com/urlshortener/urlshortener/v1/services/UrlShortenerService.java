package com.urlshortener.urlshortener.v1.services;

import org.springframework.stereotype.Service;

@Service
public class UrlShortenerService {

    public String createShortUrl(String originalUrl) {
        return "shortUrl for " + originalUrl; // Placeholder return value
    }

    public String redirectToOriginalUrl(String shortUrl) {
        // Logic to retrieve the original URL from the short URL
        return "originalUrl: " + shortUrl; // Placeholder return value
    }
    
}
