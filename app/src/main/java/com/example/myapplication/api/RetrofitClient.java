package com.example.myapplication.api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.InetAddress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient{

    private static final String BASE_URL = "http://192.168.77.151/API/";
    //192.168.231.179 mang dien thoai
    //192.168.1.7 may shuu
    //192.168.77.219 may huy
    private static Retrofit retrofit;
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
