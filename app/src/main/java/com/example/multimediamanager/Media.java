package com.example.multimediamanager;

public class Media {
    private String title;
    private String type;
    private String creationDate;
    private int image;

    public Media(String title, String type, String creationDate, int image) {
        this.title = title;
        this.type = type;
        this.creationDate = creationDate;
        this.image = image;
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

    public int getImage() {return image;}
}
