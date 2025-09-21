package com.example.myapplication.chapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder>{

    private Context mContext;
    private ArrayList<Chapter> chapters;
    private IClickchapter iClickchapter;

    public ChapterAdapter(Context mContext) {
        this.mContext = mContext;
    }

    //gan du lieu
    public void setChapters(ArrayList<Chapter> chapters,IClickchapter iClickchapter) {
        this.iClickchapter = iClickchapter;
        this.chapters = chapters;
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_list,parent,false);
        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        final Chapter chapter = chapters.get(position);
        if(chapter == null)
            return;
        holder.chapterName.setText("Chapter "+(position+1)+" :"+chapter.getName());
        holder.layoutitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickchapter.onClickChapter(chapter);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(chapters != null)
            return chapters.size();
        return 0;
    }

    public class ChapterViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout layoutitem;
        TextView chapterName;
        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutitem = itemView.findViewById(R.id.chapterx);
            chapterName = itemView.findViewById(R.id.textView3);
        }
    }
}
