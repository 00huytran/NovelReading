package com.example.myapplication.chapter;

import com.google.gson.annotations.SerializedName;

public class Chapter {
    int ID;
    @SerializedName("IDnovel")
    int IDnovel;
    @SerializedName("Name")
    private String Name;


    public Chapter(int ID, int IDnovel, String Name) {
        this.ID = ID;
        this.IDnovel = IDnovel;
        this.Name = Name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getIDnovel() {
        return IDnovel;
    }

    public void setIDnovel(int IDnovel) {
        this.IDnovel = IDnovel;
    }
}
