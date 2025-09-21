package com.example.myapplication.category;

import com.example.myapplication.novel.Novel;

import java.util.ArrayList;

public class Category {
    String name;
    ArrayList<Novel>  novels;

    public Category(String name, ArrayList<Novel> novels) {
        this.name = name;
        this.novels = novels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Novel> getNovels() {
        return novels;
    }

    public void setNovels(ArrayList<Novel> novels) {
        this.novels = novels;
    }
}
