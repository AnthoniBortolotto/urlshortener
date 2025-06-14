package com.urlshortener.urlshortener.v1.controllers;

import com.urlshortener.urlshortener.dto.UrlRequest;
import com.urlshortener.urlshortener.dto.OriginalUrlResponse;
import com.urlshortener.urlshortener.entities.UrlMapping;

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
    public UrlMapping createShortUrl(@Valid @RequestBody UrlRequest request) {
        return this.UrlShortenerService.createShortUrl(request.getUrl());
    }

    @GetMapping("/redirect/{shortUrl}")
    public OriginalUrlResponse getOriginalUrl(@PathVariable String shortUrl) {
        return this.UrlShortenerService.getOriginalUrl(shortUrl);
    }
} 