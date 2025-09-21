package com.example.myapplication.reading;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ReadingAdapter extends RecyclerView.Adapter<ReadingAdapter.ReadingViewHolder> {

    private Context mContext;
    private ArrayList<Reading> readings;


    public ReadingAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setReadings(ArrayList<Reading> readings) {

        this.readings = readings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReadingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.readingcontent,parent,false);
        return new ReadingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReadingViewHolder holder, int position) {
        final Reading reading = readings.get(position);
        if(reading == null)
            return;
        String url = reading.getImg();
        if(!url.isEmpty()) Glide.with(holder.itemView).load(url).into(holder.readingImg);
        if(!reading.getContent().isEmpty()) holder.readingContent.setText(reading.getContent());
    }

    @Override
    public int getItemCount() {
        if(readings != null)
            return readings.size();
        return 0;
    }

    public class ReadingViewHolder extends RecyclerView.ViewHolder {
        TextView readingContent;
        ImageView readingImg;
        public ReadingViewHolder(@NonNull View itemView) {
            super(itemView);
            readingContent = itemView.findViewById(R.id.content_list);
            readingImg = itemView.findViewById(R.id.image_list_content);
        }
    }
}
