package com.urlshortener.urlshortener.v1.controllers;

import com.urlshortener.urlshortener.dto.UrlRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import com.urlshortener.urlshortener.v1.services.UrlShortenerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1")
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService UrlShortenerService;

    @PostMapping("/shorten")
    public String createShortUrl(@Valid @RequestBody UrlRequest request) {
        return this.UrlShortenerService.createShortUrl(request.getUrl());
    }

    @GetMapping("/redirect/{shortUrl}")
    public String redirectToOriginalUrl(@PathVariable String shortUrl) {
        return this.UrlShortenerService.redirectToOriginalUrl(shortUrl);
    }
} 