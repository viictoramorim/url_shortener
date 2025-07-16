package com.urlshortener.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.view.RedirectView;

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
    public RedirectView redirect(@PathVariable String code){
        return service.getOriginalUrl(code)
            .map(originaUrl -> {
                RedirectView redirectView = new RedirectView();
                redirectView.setUrl(originaUrl);
                return redirectView;
            })
            .orElseGet(()-> {
                RedirectView redirectView = new RedirectView();
                redirectView.setUrl("?not-found.html"); // will show a simple error page
                return redirectView;
            });
    }
}
