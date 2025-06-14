package com.urlshortener.urlshortener.v1.services;

import com.urlshortener.urlshortener.entities.UrlMapping;
import com.urlshortener.urlshortener.repositories.UrlMappingRepository;
import com.urlshortener.urlshortener.dto.OriginalUrlResponse;
import com.urlshortener.urlshortener.exceptions.InvalidUrlException;
import com.urlshortener.urlshortener.exceptions.ShortUrlNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UrlShortenerServiceTest {

    @Mock
    private UrlMappingRepository urlMappingRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    private UrlShortenerService urlShortenerService;

    @BeforeEach
    void setUp() {
        urlShortenerService = new UrlShortenerService(urlMappingRepository, mongoTemplate);
    }

    @Test
    void createShortUrl_WithNewUrl_ShouldCreateNewMapping() {
        // Arrange
        String originalUrl = "https://example.com";
        when(urlMappingRepository.existsByOriginalUrl(originalUrl)).thenReturn(false);
        when(urlMappingRepository.save(any(UrlMapping.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        UrlMapping result = urlShortenerService.createShortUrl(originalUrl);

        // Assert
        assertNotNull(result);
        assertEquals(originalUrl, result.getOriginalUrl());
        assertNotNull(result.getShortUrl());
        verify(urlMappingRepository).save(any(UrlMapping.class));
    }

    @Test
    void createShortUrl_WithExistingUrl_ShouldReturnExistingMapping() {
        // Arrange
        String originalUrl = "https://example.com";
        UrlMapping existingMapping = new UrlMapping(originalUrl);
        when(urlMappingRepository.existsByOriginalUrl(originalUrl)).thenReturn(true);
        when(urlMappingRepository.findByOriginalUrl(originalUrl)).thenReturn(Optional.of(existingMapping));

        // Act
        UrlMapping result = urlShortenerService.createShortUrl(originalUrl);

        // Assert
        assertNotNull(result);
        assertEquals(existingMapping, result);
        verify(urlMappingRepository, never()).save(any(UrlMapping.class));
    }

    @Test
    void createShortUrl_WithInvalidUrl_ShouldThrowException() {
        // Arrange
        String invalidUrl = "not-a-valid-url";

        // Act & Assert
        assertThrows(InvalidUrlException.class, () -> urlShortenerService.createShortUrl(invalidUrl));
        verify(urlMappingRepository, never()).save(any(UrlMapping.class));
    }

    @Test
    void getOriginalUrl_WithValidShortUrl_ShouldReturnOriginalUrl() {
        // Arrange
        String shortUrl = "abc123";
        String originalUrl = "https://example.com";
        UrlMapping urlMapping = new UrlMapping(originalUrl);
        urlMapping.setShortUrl(shortUrl);
        
        when(urlMappingRepository.findById(shortUrl)).thenReturn(Optional.of(urlMapping));
        when(mongoTemplate.updateFirst(any(Query.class), any(Update.class), eq(UrlMapping.class))).thenReturn(null);

        // Act
        OriginalUrlResponse result = urlShortenerService.getOriginalUrl(shortUrl);

        // Assert
        assertNotNull(result);
        assertEquals(originalUrl, result.getOriginalUrl());
        verify(mongoTemplate).updateFirst(any(Query.class), any(Update.class), eq(UrlMapping.class));
    }

    @Test
    void getOriginalUrl_WithInvalidShortUrl_ShouldThrowException() {
        // Arrange
        String invalidShortUrl = "nonexistent";
        when(urlMappingRepository.findById(invalidShortUrl)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ShortUrlNotFoundException.class, () -> urlShortenerService.getOriginalUrl(invalidShortUrl));
        verify(mongoTemplate, never()).updateFirst(any(Query.class), any(Update.class), eq(UrlMapping.class));
    }

    @Test
    void getOriginalUrl_ShouldUpdateAccessStatistics() {
        // Arrange
        String shortUrl = "abc123";
        String originalUrl = "https://example.com";
        UrlMapping urlMapping = new UrlMapping(originalUrl);
        urlMapping.setShortUrl(shortUrl);
        
        when(urlMappingRepository.findById(shortUrl)).thenReturn(Optional.of(urlMapping));
        when(mongoTemplate.updateFirst(any(Query.class), any(Update.class), eq(UrlMapping.class))).thenReturn(null);

        // Act
        urlShortenerService.getOriginalUrl(shortUrl);

        // Assert
        verify(mongoTemplate).updateFirst(
            argThat(query -> query.toString().contains(shortUrl)),
            argThat(update -> 
                update.toString().contains("lastAccessedAt") && 
                update.toString().contains("accessCount")
            ),
            eq(UrlMapping.class)
        );
    }
} 