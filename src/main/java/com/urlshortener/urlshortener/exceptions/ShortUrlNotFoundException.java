package com.urlshortener.urlshortener.exceptions;

public class ShortUrlNotFoundException extends RuntimeException {
    public ShortUrlNotFoundException(String shortUrl) {
        super("Short URL not found: " + shortUrl);
    }
} 