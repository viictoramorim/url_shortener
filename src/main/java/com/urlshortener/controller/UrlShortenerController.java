package com.urlshortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.urlshortener.service.UrlShortenerService;

@RestController
@RequestMapping("/api")
public class UrlShortenerController {
    
    @Autowired
    private UrlShortenerService service;

    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam String url){
        String shortCode = service.ShortenUrl(url);
        return "http://localhost:8080/" + shortCode;
    }

    @GetMapping("/{code}")
    public String redirect(@PathVariable String code){
        return service.getOriginalUrl(code)
            .orElse("URL not found");
    }
}
