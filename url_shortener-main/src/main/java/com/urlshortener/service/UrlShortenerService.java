package com.urlshortener.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.urlshortener.model.ShortUrl;
import com.urlshortener.repository.ShortUrlRepository;

@Service
public class UrlShortenerService {
    private static final String CHARACTERS = "amsjdjkkdhredcndbifviabbrifvboivaiborfvfroa0987546";
    private static final int CODE_LENGTH = 6;
    private final SecureRandom random = new SecureRandom();

    @Autowired
    private ShortUrlRepository repository;
    
    private String generateShortCode(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++){
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    public String shortenUrl(String originalUrl){
        if (!isValidUrl(originalUrl)) {
            throw new IllegalArgumentException("URL invalible");
        }
        Optional<ShortUrl> existing = repository.findAll().stream()
            .filter(url -> url.getOriginalUrl().equals(originalUrl))
            .findFirst();

        if (existing.isPresent()) {
            return existing.get().getShortCode();
        }
        String shortCode;
        do{
            shortCode = generateShortCode();
        }while (repository.findByShortCode(shortCode).isPresent());

        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(originalUrl);
        shortUrl.setShortCode(shortCode);
        shortUrl.setCreatedAt(LocalDateTime.now().toString());
    
        repository.save(shortUrl);

        return shortCode;
    }

    private boolean isValidUrl(String url){
        return url.startsWith("http://") || url.startsWith("https://");
    }

    public Optional<String> getOriginalUrl(String code) {
        return repository.findByShortCode(code)
                .map(ShortUrl::getOriginalUrl);
    }
}
