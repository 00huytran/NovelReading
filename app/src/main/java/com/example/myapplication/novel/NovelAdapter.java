package com.example.myapplication.novel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.NovelInfor;
import com.example.myapplication.R;

import java.util.ArrayList;

public class NovelAdapter extends RecyclerView.Adapter<NovelAdapter.BooKViewHolder> {
    private ArrayList<Novel> novel;
    private IClickcard iClickcard;

    public void setData(ArrayList<Novel> novel,IClickcard listener){
        this.novel = novel;
        this.iClickcard= listener;
        notifyDataSetChanged();
    }

    
    @NonNull
    @Override
    public BooKViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        return new BooKViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooKViewHolder holder, int position) {
        final Novel nv = novel.get(position);
        if(nv == null){
            return;
        }
        String url = nv.getImg();
        Glide.with(holder.itemView).load(url).into(holder.img);
        holder.name.setText(nv.getName());
        holder.novelcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickcard.onClickItemcard(nv);
            }
        });
    }
    @Override
    public int getItemCount() {
        if(novel != null){
            return novel.size();
        }
        return 0;
    }

    public class BooKViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout novelcard;

        private ImageView img;
        private TextView name;

        public BooKViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.card_img);
            name = itemView.findViewById(R.id.card_name);
            novelcard = itemView.findViewById(R.id.novelcard);

        }
    }
}
