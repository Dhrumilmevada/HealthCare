package com.example.dhrumil.healthcare.homePage.news.model;

import java.util.ArrayList;

/**
 * Created by Dhrumil on 3/6/2018.
 */

public class Articles {


    public String author;
    public String title;
    public String decription;
    public String url;
    public String urlToImage;
    public String publishedAt;

    public Articles(String author, String title, String decription, String url, String urlToImage, String publishedAt) {
        this.author = author;
        this.title = title;
        this.decription = decription;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;

    }



    public Articles() {
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDecription() {
        return decription;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }
}
