package com.example.myapplication.api;

import com.google.gson.annotations.SerializedName;

public class RatingResponse {
    @SerializedName("AVG(rating)")
    private float AVG ;

    public float getAVG() {
        return AVG;
    }

    public void setAVG(float AVG) {
        this.AVG = AVG;
    }
}
