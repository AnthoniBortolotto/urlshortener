package com.urlshortener.urlshortener.repositories;

import com.urlshortener.urlshortener.entities.UrlMapping;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlMappingRepository extends MongoRepository<UrlMapping, String> {
    Optional<UrlMapping> findByOriginalUrl(String originalUrl);
    boolean existsByOriginalUrl(String originalUrl);
} 