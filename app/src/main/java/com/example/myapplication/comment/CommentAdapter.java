package com.example.myapplication.comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.chapter.Chapter;

import java.util.ArrayList;

public class CommentAdapter extends  RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

    private Context mContext;
    private ArrayList<Comment> comments;

    public  CommentAdapter(Context mContext){this.mContext = mContext;}
    public void setComments(ArrayList<Comment> comments){
        this.comments = comments;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.name.setText(comment.getUser());
        holder.comment.setText(comment.getContent());
        holder.rate.setRating(comment.getRating());

    }

    @Override
    public int getItemCount() {
        if(comments !=null){
            return  comments.size();
        }
        return 0;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutitem;

        TextView name,comment;
        RatingBar rate;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtuser_comment);
            comment = itemView.findViewById(R.id.txtTextComment);
            rate = itemView.findViewById(R.id.rbrcomment);
        }
    }
}
