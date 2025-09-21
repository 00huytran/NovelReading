package com.example.myapplication.reading;

import com.google.gson.annotations.SerializedName;

public class Reading {
    private int id;
    private int idchapter;
    private String img;
    @SerializedName("textcontent")
    private String content;

    public Reading(int id, int idchapter, String img, String content) {
        this.id = id;
        this.idchapter = idchapter;
        this.img = img;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdchapter() {
        return idchapter;
    }

    public void setIdchapter(int idchapter) {
        this.idchapter = idchapter;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
