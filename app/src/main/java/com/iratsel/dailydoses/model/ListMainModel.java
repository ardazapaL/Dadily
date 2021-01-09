package com.iratsel.dailydoses.model;

import android.graphics.Bitmap;

public class ListMainModel {

    private int id;
    private Bitmap image;
    private String date;
    private String headline;
    private String description;

    public ListMainModel(int id, Bitmap image, String date, String headline, String description) {
        this.id = id;
        this.image = image;
        this.date = date;
        this.headline = headline;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
