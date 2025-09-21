package com.example.myapplication.comment;

public class Comment {
    String user;
    float rating;
    String content;

    public Comment(String user, float rating, String content) {
        this.user = user;
        this.rating = rating;
        this.content = content;
    }

    public String getUser() {
        return user;
    }

    public float getRating() {
        return rating;
    }

    public String getContent() {
        return content;
    }
}
