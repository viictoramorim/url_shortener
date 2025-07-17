package com.urlshortener.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "short_urls")
public class ShortUrl{
    @Id
    private String id;  // id of mongoDB 

    private String originalUrl;
    private String shortCode;
    private String createdAt;

    
}
