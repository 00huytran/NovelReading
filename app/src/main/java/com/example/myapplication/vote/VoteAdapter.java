package com.example.myapplication.vote;

import java.util.ArrayList;

public class VoteAdapter extends ArrayList<Vote> {
    private ArrayList<Vote> votes;
    public float checkVote(){
        float sum = 0;
        for (Vote a:votes){
            sum+= a.getRating();
        }
        float count = votes.size();
        float avg = sum/count;
        return avg;
    }

}
