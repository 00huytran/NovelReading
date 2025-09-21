package com.example.myapplication.novel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Novel {
    @SerializedName("ID")
    int id;
    String name,img,author,discription;



    public Novel() {
    }

    public Novel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Novel(String name, String img) {
        this.name = name;
        this.img = img;
    }

    public Novel(int id, String name, String img, String author, String discription, int chapters) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.author = author;
        this.discription = discription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }


}
