package com.sux.utility;

/**
 * Created by Shorty on 5/12/2014.
 */
public class Result {
    String title, subTitle, id, image;

    public Result(String title, String subTitle, String id, String image){
        this.title = title;
        this.subTitle = subTitle;
        this.id = id;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
