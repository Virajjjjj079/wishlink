package com.example.wishlink_backend.controller;

public class ScrapeRequest {

    private String url;
    private Integer userId;
    

    // Getters and setters

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    
}
