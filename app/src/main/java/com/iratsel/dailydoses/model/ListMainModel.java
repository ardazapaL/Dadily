package com.iratsel.dailydoses.model;

public class ListMainModel {

    private String date;
    private String headline;
    private String description;

    public ListMainModel(String date, String headline, String description) {
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
}
