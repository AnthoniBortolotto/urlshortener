package com.urlshortener.urlshortener.exceptions;

public class InvalidUrlException extends RuntimeException {
    public InvalidUrlException(String url) {
        super("Invalid URL format: " + url);
    }
} 