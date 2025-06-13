package com.urlshortener.urlshortener.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/urlshortener")
public class UrlShortenerController {

    @PostMapping("/shorten")
    public String createShortUrl(@RequestBody String entity) {
        return "shortUrl"; // Placeholder return value
    }

    @GetMapping("/redirect/{shortUrl}")
    public String redirectToOriginalUrl(@PathVariable String shortUrl) {
        return "originalUrl: " + shortUrl; // Placeholder return value
    }
}