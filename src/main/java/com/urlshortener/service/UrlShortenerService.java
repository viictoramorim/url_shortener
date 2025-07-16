package com.urlshortener.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urlshortener.model.ShortUrl;
import com.urlshortener.repository.ShortUrlRepository;

@Service
public class UrlShortenerService {
    @Autowired
    private ShortUrlRepository repository;

    public String ShortenUrl(String originalUrl){
        //check if this shortened URL already exists
        Optional<ShortUrl> existing = repository.findAll().stream()
            .filter(url -> url.getOriginalURL().equals(originalUrl))
            .findFirst();
        
        if (existing.isPresent()) {
            return existing.get().getShortCode(); // return the exesting shortCode
        }

        // Generates shortcode
        String shortCode = UUID.randomUUID().toString().substring(0, 6);

        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setOriginalURL(originalUrl);
        shortUrl.setShortCode(shortCode);
        shortUrl.setCreatedAt(LocalDateTime.now().toString());

        repository.save(shortUrl);
        
        return shortCode;
    }

    public Optional<String> getOriginalUrl(String shortCode){
        return repository.findByShortCode(shortCode)
            .map(ShortUrl::getOriginalURL);
    }
}
