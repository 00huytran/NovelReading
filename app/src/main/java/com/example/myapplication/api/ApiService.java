package com.example.myapplication.api;

import com.example.myapplication.chapter.Chapter;
import com.example.myapplication.classobject.Account;
import com.example.myapplication.comment.Comment;
import com.example.myapplication.novel.Novel;
import com.example.myapplication.reading.Reading;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    //đăng nhập
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> loginUser(
            @Field("username") String username,
            @Field("password") String password
    );

    //đăng kí
    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponse> RegisterUser(
            @Field("username") String username,
            @Field("password") String password,
            @Field("email") String email
    );

    //timf kiếm truyện
    @FormUrlEncoded
    @POST("getresearchnovel.php")
    Call<ArrayList<Novel>> GetlistSearch(
            @Field("name") String name
    );
    //lấy top 5 truyên đầu tiên
    @FormUrlEncoded
    @POST("gettopnovel.php")
    Call<ArrayList<Novel>> Getlist5(@Field("") String dummy);

    //lấy truyên
    @FormUrlEncoded
    @POST("getnovel.php")
    Call<ArrayList<Novel>> Getlist(@Field("") String dummy);

    @FormUrlEncoded
    @POST("loaduser.php")
    Call<Account> Getuser(@Field("username") String username);

    //đánh giá truyện
    @FormUrlEncoded
    @POST("Voting.php")
    Call<VoteResponse> VoteResp(
            @Field("idacc") int idacc,
            @Field("idnovel") int idnovel,
            @Field("rating")  float rating
    );

    //Truyện được đánh giá
    @FormUrlEncoded
    @POST("takeAVGrate.php")
    Call<RatingResponse> RateofNovel(
        @Field("idnovel") int idnovel
    );
    //gửi đánh giá
    @FormUrlEncoded
    @POST("sendmessage.php")
    Call<CommentResponse> CommentRes(
            @Field("idnovel") int idnovel,
            @Field("idacc") int idacc,
            @Field("content") String content
    );
    //Lấy list chapter truyện
    @FormUrlEncoded
    @POST("getchapter.php")
    Call<ArrayList<Chapter>> getChapter(
            @Field("idnovel") int idnovel
    );

    //xác nhận lịch sử đọc
    @FormUrlEncoded
    @POST("checkbookmark.php")
    Call<BookmarkResponse> checkBookMark(
            @Field("idacc") int idacc,
            @Field("idnovel") int idnovel,
            @Field("idchapter") int idchapter
    );
    //lấy trang đọc
    @FormUrlEncoded
    @POST("getreadingpage.php")
    Call<ArrayList<Reading>> getReadingPage(
            @Field("idchapter") int idchapter
    );

    //lấy lần cuối đọc trang
    @FormUrlEncoded
    @POST("getLatestmarked.php")
    Call<LastSeenResponse> getlastSeen(
            @Field("idacc") int idacc
    );
    //list comment
    @FormUrlEncoded
    @POST("getlistmess.php")
    Call<ArrayList<Comment>> getListComment(
            @Field("idnovel") int idnovel
    );
    @FormUrlEncoded
    @POST("getname.php")
    Call<AccountExitResponse> IsaccExit(
            @Field("username") String username);

    @FormUrlEncoded
    @POST("newpassword.php")
    Call<ChangePassResponse> changePassmess(
            @Field("username") String username,
            @Field("password") String password
        );



}
