package com.mrhi2018.ex60rssfeed;

public class Item {

    String title;
    String link;
    String desc;
    String image;
    String date;

    public Item(){
    }

    public Item(String title, String link, String desc, String image, String date) {
        this.title = title;
        this.link = link;
        this.desc = desc;
        this.image = image;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
