package com.example.multimediamanager;

import android.graphics.Bitmap;

public class Media {
    private String title;
    private String type;
    private String creationDate;
    private String image;
    private boolean favorite;
    private String tag1, tag2, tag3;

    public Media(String title, String type, String creationDate, String image, boolean fav) {
        this.title = title;
        this.type = type;
        this.creationDate = creationDate;
        this.image = image;
        this.favorite = fav;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getImage() {return image;}

    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
