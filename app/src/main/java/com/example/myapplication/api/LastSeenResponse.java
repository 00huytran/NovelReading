package com.example.myapplication.api;

import com.google.gson.annotations.SerializedName;

public class LastSeenResponse {
    @SerializedName("IDnovel")
    int IDnovel;
    @SerializedName("novelname")
    String novelname;
    @SerializedName("IDchapter")
    int IDchapter;
    @SerializedName("chaptername")
    String chaptername;

    public LastSeenResponse() {
    }

    public LastSeenResponse(int IDnovel, String novelname, int IDchapter, String chaptername) {
        this.IDnovel = IDnovel;
        this.novelname = novelname;
        this.IDchapter = IDchapter;
        this.chaptername = chaptername;
    }

    public int getIDnovel() {
        return IDnovel;
    }

    public String getNovelname() {
        return novelname;
    }

    public int getIDchapter() {
        return IDchapter;
    }

    public String getChaptername() {
        return chaptername;
    }

    public void setIDnovel(int IDnovel) {
        this.IDnovel = IDnovel;
    }

    public void setNovelname(String novelname) {
        this.novelname = novelname;
    }

    public void setIDchapter(int IDchapter) {
        this.IDchapter = IDchapter;
    }

    public void setChaptername(String chaptername) {
        this.chaptername = chaptername;
    }
}
