package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.api.ApiService;
import com.example.myapplication.api.BookmarkResponse;
import com.example.myapplication.api.CommentResponse;
import com.example.myapplication.api.RatingResponse;
import com.example.myapplication.api.RetrofitClient;
import com.example.myapplication.api.VoteResponse;
import com.example.myapplication.chapter.Chapter;
import com.example.myapplication.chapter.ChapterAdapter;
import com.example.myapplication.chapter.IClickchapter;
import com.example.myapplication.classobject.Account;
import com.example.myapplication.comment.Comment;
import com.example.myapplication.comment.CommentAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NovelInfor extends AppCompatActivity {
    private RecyclerView rcvChapter,rsvComment;
    private ChapterAdapter chapterAdapter;
    private CommentAdapter commentAdapter;
    private ArrayList<Chapter> lstChapter;
    private ArrayList<Comment> lstComment;

    private int valueShowidnovel;
    private TextView countchap;

    private RatingBar vote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel_infor);

        ImageView imageView = findViewById(R.id.imginfor);
        TextView title = findViewById(R.id.title_name);
        TextView info  = findViewById(R.id.name_infor);
        TextView aut = findViewById(R.id.authortxt);
        TextView discrp = findViewById(R.id.discriptxt);
        countchap = findViewById(R.id.chaptertxt);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        valueShowidnovel = bundle.getInt("idnovel");
        String valueShowname = bundle.getString( "name");
        String valueShowimg = bundle.getString("img");
        String valueShowauthor = bundle.getString("aut");
        String valueShowdiscrip = bundle.getString("discrip");
        title.setText(valueShowname);
        info.setText(valueShowname);
        Glide.with(this).load(valueShowimg).into(imageView);
        aut.setText("Tác Giả: "+valueShowauthor);
        discrp.setText(valueShowdiscrip);

        TextView goback = findViewById(R.id.go_back);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //lấy đánh giá
        getVoteofNovel(valueShowidnovel);

//       đánh giá truyện
        Account account = Account.getInstance();
        int idacc =  account.getId();

        vote = findViewById(R.id.ratingBar);

        TextView rate = findViewById(R.id.ratingtxt);

        Button danhgia = findViewById(R.id.btndanhgia);
        danhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float result = vote.getRating();
                takeVote(idacc,valueShowidnovel,result);
            }
        });

//       comment
        Button submit = findViewById(R.id.btnsubmitmess);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText comment = findViewById(R.id.editTextComment);
                String content = comment.getText().toString();
                sendComment(valueShowidnovel,idacc,content);
            }
        });

        rcvChapter = findViewById(R.id.rcvChapter);
        rsvComment = findViewById(R.id.rcvListcom);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //Recycle view chapter

        chapterAdapter = new ChapterAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvChapter.setLayoutManager(linearLayoutManager);
        getListChapter(valueShowidnovel);

//        listcomment

        commentAdapter = new CommentAdapter(this);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rsvComment.setLayoutManager(linearLayoutManager1);
        getListComment(valueShowidnovel);
    }
    private void getListComment(int idnovel) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class );
        Call<ArrayList<Comment>> call = apiService.getListComment(idnovel);
        call.enqueue(new Callback<ArrayList<Comment>>() {
            @Override
            public void onResponse(Call<ArrayList<Comment>> call, Response<ArrayList<Comment>> response) {
                if(response.isSuccessful()){
                    lstComment = response.body();
                    commentAdapter.setComments(lstComment);
                    rsvComment.setAdapter(commentAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Comment>> call, Throwable t) {

            }
        });
    }



    private void getListChapter(int idNovel) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ArrayList<Chapter>> call = apiService.getChapter(idNovel);
        call.enqueue(new Callback<ArrayList<Chapter>>() {
            @Override
            public void onResponse(Call<ArrayList<Chapter>> call, Response<ArrayList<Chapter>> response) {
                if(response.isSuccessful()){
                    lstChapter = new ArrayList<>();
                    lstChapter = response.body();

                    int count = lstChapter.size();
                    countchap.setText(String.valueOf(count));
                    chapterAdapter.setChapters(lstChapter, new IClickchapter() {
                        @Override
                        public void onClickChapter(Chapter chapter) {
                            Account account = Account.getInstance();
                            int idacc =  account.getId();
                            onClickDetail( idacc , chapter.getIDnovel(), chapter.getID());
                            Intent intent1 = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putString("key_from_activity","Novelinfor");
                            bundle.putInt("key_id",chapter.getID());
                            bundle.putString("key_chapter",chapter.getName());
                            intent1.putExtras(bundle);
                            intent1.setClass(NovelInfor.this, ReadingPage.class);
                            startActivity(intent1);
                        }
                    });
                    rcvChapter.setAdapter(chapterAdapter);
                    }else {
                        Toast.makeText(NovelInfor.this, "không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onFailure(Call<ArrayList<Chapter>> call, Throwable t) {
                Toast.makeText(NovelInfor.this,  t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickDetail(int idacc, int iDnovel, int idchapter) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<BookmarkResponse> call = apiService.checkBookMark(idacc,iDnovel,idchapter);
        call.enqueue(new Callback<BookmarkResponse>() {
            @Override
            public void onResponse(Call<BookmarkResponse> call, Response<BookmarkResponse> response) {
                BookmarkResponse bookmarkResponse = response.body();
                if(response.isSuccessful()){
                    if(bookmarkResponse.isRead()){
                        Toast.makeText(NovelInfor.this, bookmarkResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(NovelInfor.this, bookmarkResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }else{
                    // Xử lý khi có lỗi trên máy chủ
                    Toast.makeText(NovelInfor.this, "Server error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookmarkResponse> call, Throwable t) {
                Toast.makeText(NovelInfor.this,  t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void takeVote(int idacc,int idnovel,float rating){
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<VoteResponse> call = apiService.VoteResp(idacc, idnovel, rating);
        call.enqueue(new Callback<VoteResponse>() {
            @Override
            public void onResponse(Call<VoteResponse> call, Response<VoteResponse> response) {
                VoteResponse voteResponse = response.body();
                if (response.isSuccessful()) {
                    if (voteResponse.isSuccess()) {
                        Toast.makeText(NovelInfor.this, voteResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(NovelInfor.this, voteResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Xử lý khi có lỗi trên máy chủ
                    Toast.makeText(NovelInfor.this, "Server error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<VoteResponse> call, Throwable t) {
                Toast.makeText(NovelInfor.this,  t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void getVoteofNovel(int idnovel){
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<RatingResponse> call = apiService.RateofNovel(idnovel);
        call.enqueue(new Callback<RatingResponse>() {
            @Override
            public void onResponse(Call<RatingResponse> call, Response<RatingResponse> response) {
                if(response.isSuccessful()){
                    RatingResponse ratingResponse = response.body();
                    String x = String.format("%.1f",ratingResponse.getAVG());
                    TextView vote = findViewById(R.id.ratingtxt);
                    vote.setText(x+"/5");
                }else {
                    Toast.makeText(NovelInfor.this, "không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RatingResponse> call, Throwable t) {
                Toast.makeText(NovelInfor.this,  t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void sendComment(int idnovel,int idacc,String content){
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<CommentResponse> call = apiService.CommentRes(idnovel,idacc,content);
        call.enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                if(response.isSuccessful()){
                    CommentResponse commentResponse = response.body();
                    if(commentResponse.isSuccess()) {

                        Toast.makeText(NovelInfor.this,commentResponse.getMessage(),Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(NovelInfor.this, commentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(NovelInfor.this,"Can not connect to server",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                Toast.makeText(NovelInfor.this,  t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}