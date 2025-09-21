package com.example.myapplication.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.NovelInfor;
import com.example.myapplication.R;
import com.example.myapplication.classobject.Account;
import com.example.myapplication.novel.IClickcard;
import com.example.myapplication.novel.Novel;
import com.example.myapplication.novel.NovelAdapter;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends  RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context context;
    private ArrayList<Category> lstCategory;
    public CategoryAdapter(Context context){
        this.context = context;

    }
    public void setData(ArrayList<Category> lst){
        this.lstCategory = lst;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category,parent,false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = lstCategory.get(position);
        if(category==null){
            return;
        }
        holder.tvname.setText(category.getName());
        LinearLayoutManager linearlayoutmanager =new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        holder.rcNovel.setLayoutManager(linearlayoutmanager);

        NovelAdapter noveladapter= new NovelAdapter();
        noveladapter.setData(category.getNovels(), new IClickcard() {
            @Override
            public void onClickItemcard(Novel novel) {
                OnclickGotoDetail(novel);
            }
        });
        holder.rcNovel.setAdapter(noveladapter);


    }

    @Override
    public int getItemCount() {
        if(lstCategory != null){
            return lstCategory.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        private TextView tvname;
        private  RecyclerView rcNovel;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.category);
            rcNovel = itemView.findViewById(R.id.recycle_view_novel);
        }
    }
    private void OnclickGotoDetail(Novel novel){
        Intent i = new Intent(context, NovelInfor.class);
        Bundle bundle = new Bundle();
        bundle.putInt("idnovel",novel.getId());
        bundle.putString("name", novel.getName());
        bundle.putString("img", novel.getImg());
        bundle.putString("aut", novel.getAuthor());
        bundle.putString("discrip", novel.getDiscription());
        i.putExtras(bundle);
        context.startActivity(i);
    }


}
