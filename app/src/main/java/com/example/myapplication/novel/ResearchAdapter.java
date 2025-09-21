package com.example.myapplication.novel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.NovelInfor;
import com.example.myapplication.R;
import com.example.myapplication.reading.ReadingAdapter;

import java.util.ArrayList;

public class ResearchAdapter extends RecyclerView.Adapter<ResearchAdapter.BooKViewHolder> {
    private Context mContext;
    private ArrayList<Novel> novel;
    private IClickresearch iClickresearch;

    public ResearchAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(ArrayList<Novel> novel){
        this.novel = novel;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public BooKViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listnovel,parent,false);
        return new BooKViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResearchAdapter.BooKViewHolder holder, int position) {
        final Novel nv = novel.get(position);
        if(nv == null){
            return;
        }
        holder.name.setText(nv.getName());
        holder.novelcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnclickGotoDetail(nv);
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
        TextView name;
        LinearLayout novelcard;
        public BooKViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.obj_listname);
            novelcard = itemView.findViewById(R.id.novellist);
        }
    }
    private void OnclickGotoDetail(Novel novel){
        Intent i = new Intent(mContext, NovelInfor.class);
        Bundle bundle = new Bundle();
        bundle.putInt("idnovel",novel.getId());
        bundle.putString("name", novel.getName());
        bundle.putString("img", novel.getImg());
        bundle.putString("aut", novel.getAuthor());
        bundle.putString("discrip", novel.getDiscription());
        i.putExtras(bundle);
        mContext.startActivity(i);
    }
}
