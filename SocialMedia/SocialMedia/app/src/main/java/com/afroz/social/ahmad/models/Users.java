package com.afroz.social.ahmad.models;

/**
 * Created by afroz on 22/2/18.
 */

public class Users extends UserId {

    private String name,image;

    public Users(){

    }

    public Users(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
