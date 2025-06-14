package com.urlshortener.urlshortener.v1.services;

import com.urlshortener.urlshortener.entities.UrlMapping;
import com.urlshortener.urlshortener.repositories.UrlMappingRepository;
import com.urlshortener.urlshortener.dto.OriginalUrlResponse;
import com.urlshortener.urlshortener.exceptions.ShortUrlNotFoundException;
import com.urlshortener.urlshortener.exceptions.InvalidUrlException;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import java.time.LocalDateTime;
import java.net.URL;
import java.net.MalformedURLException;

@Service
public class UrlShortenerService {

    private final UrlMappingRepository urlMappingRepository;
    private final MongoTemplate mongoTemplate;

    UrlShortenerService(UrlMappingRepository urlMappingRepository, MongoTemplate mongoTemplate) {
        this.urlMappingRepository = urlMappingRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public UrlMapping createShortUrl(String originalUrl) {
        validateUrl(originalUrl);
        UrlMapping urlMapping = new UrlMapping(originalUrl);
        urlMappingRepository.save(urlMapping);
        return urlMapping;
    }

    public OriginalUrlResponse getOriginalUrl(String shortUrl) {
        UrlMapping urlMapping = urlMappingRepository.findById(shortUrl)
            .orElseThrow(() -> new ShortUrlNotFoundException(shortUrl));
        
        // Atomically update access statistics
        Query query = new Query(Criteria.where("_id").is(shortUrl));
        Update update = new Update()
            .set("lastAccessedAt", LocalDateTime.now())
            .inc("accessCount", 1);
        
        mongoTemplate.updateFirst(query, update, UrlMapping.class);
        
        return new OriginalUrlResponse(urlMapping.getOriginalUrl());
    }

    private void validateUrl(String url) {
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            throw new InvalidUrlException(url);
        }
    }
}
