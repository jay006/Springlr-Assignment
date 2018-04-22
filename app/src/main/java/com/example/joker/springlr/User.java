package com.example.joker.springlr;

public class User {

    private String userName,userEmail,imageURL,id;

    public User(String userName, String userEmail, String imageURL, String id) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.imageURL = imageURL;
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getId() {
        return id;
    }
}
