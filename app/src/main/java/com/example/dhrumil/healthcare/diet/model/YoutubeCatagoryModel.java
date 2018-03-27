package com.example.dhrumil.healthcare.diet.model;

/**
 * Created by Dhrumil on 3/24/2018.
 */

public class YoutubeCatagoryModel {

    private String title ,desc;
    int image;

    public YoutubeCatagoryModel() {
    }

    public YoutubeCatagoryModel(String title, String desc, int image) {
        this.title = title;
        this.desc = desc;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
