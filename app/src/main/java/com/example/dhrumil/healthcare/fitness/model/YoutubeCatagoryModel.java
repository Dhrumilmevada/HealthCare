package com.example.dhrumil.healthcare.fitness.model;

/**
 * Created by Dhrumil on 3/26/2018.
 */

public class YoutubeCatagoryModel {

    String title;
    int image;

    public YoutubeCatagoryModel() {
    }

    public YoutubeCatagoryModel(int image, String title) {
        this.image = image;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
