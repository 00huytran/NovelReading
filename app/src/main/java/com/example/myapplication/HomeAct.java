package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.api.ApiService;
import com.example.myapplication.api.LastSeenResponse;
import com.example.myapplication.api.RetrofitClient;
import com.example.myapplication.category.Category;
import com.example.myapplication.category.CategoryAdapter;
import com.example.myapplication.classobject.Account;
import com.example.myapplication.novel.Novel;
import com.example.myapplication.novel.ResearchAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeAct extends AppCompatActivity {



    private RecyclerView rv,rvresearch;
    private CategoryAdapter categoryadapter;


    private ArrayList<Novel> lstnv5,lstnv,lstresearch;
    private ArrayList<Category> lst;

    private TextView useracc;

    private Account account ;

    private  TextView txtchapter,txtnovel;
    private CardView lastseen;

    private TextView inputsearch;

    private LastSeenResponse lastSeenResponse;
    private String valueShowname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        useracc = findViewById(R.id.usernamehome);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        valueShowname = bundle.getString("nameuser");



        txtnovel = findViewById(R.id.txtnovelname);
        txtchapter = findViewById(R.id.txtchaptername);
        lastseen = findViewById(R.id.lastseenlayout);

        account = Account.getInstance();
        account.setUsername(valueShowname);
        getUser(valueShowname);

        rv = findViewById(R.id.rcv_category);
        categoryadapter = new CategoryAdapter(this);

        //searchbar
//        research_dialog = findViewById(R.id.research_dialog);
        inputsearch = findViewById(R.id.txtsearch);
        inputsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(HomeAct.this);
                dialog.setContentView(R.layout.research_dialog);

                rvresearch = dialog.findViewById(R.id.recyclerView_list);


                SearchView find = dialog.findViewById(R.id.txtsearch1);
                find.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        getresearch(query);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {

                        getresearch(newText);
                        return true;
                    }
                });




                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
            }
        });

        LinearLayoutManager linearlayoutmanager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rv.setLayoutManager(linearlayoutmanager);

        lst = new ArrayList<>();
        getlistnovel();
        Callgettop5();


    }

    @Override
    protected void onResume() {
        super.onResume();
        lastSeenResponse = new LastSeenResponse();

        getUser(valueShowname);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void getlastseeninfor(int id) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<LastSeenResponse> call = apiService.getlastSeen(id);
        call.enqueue(new Callback<LastSeenResponse>() {
            @Override
            public void onResponse(Call<LastSeenResponse> call, Response<LastSeenResponse> response) {
                if(response.isSuccessful()){

                    lastSeenResponse = response.body();
                    if(!lastSeenResponse.getChaptername().equals("")) txtchapter.setText(lastSeenResponse.getChaptername());
                    txtnovel.setText(lastSeenResponse.getNovelname());
                    lastseen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putString("key_from_activity","HomeAct");
                            bundle.putInt("key_lastid",lastSeenResponse.getIDchapter());
                            bundle.putString("key_lastchapter",lastSeenResponse.getChaptername());
                            i.putExtras(bundle);
                            i.setClass(HomeAct.this,ReadingPage.class);
                            startActivity(i);
                        }
                    });

                }else{
                    Toast.makeText(HomeAct.this, "Không thể nhận dữ liệu từ máy chủ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LastSeenResponse> call, Throwable t) {

                // Xử lý khi có lỗi kết nối
             //   Toast.makeText(HomeAct.this,  t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void getlistnovel(){
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);


        Call<ArrayList<Novel>> call = apiService.Getlist("");
        call.enqueue(new Callback<ArrayList<Novel>>() {
            @Override
            public void onResponse(Call<ArrayList<Novel>> call, Response<ArrayList<Novel>> response) {
                if (response.isSuccessful()) {
                    // Phản hồi từ máy chủ thành công
                    lstnv = new ArrayList<>();

                    lstnv = response.body();
                    lst.add(new Category("Danh sách truyện ",lstnv));
                    categoryadapter.setData(lst);
                    rv.setAdapter(categoryadapter);

                } else {
                    // Xử lý khi có phản hồi không thành công
                    // Ví dụ: Hiển thị thông báo lỗi
                    Toast.makeText(HomeAct.this, "Không thể nhận dữ liệu từ máy chủ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Novel>> call, Throwable t) {
                // Xử lý khi có lỗi kết nối
                Toast.makeText(HomeAct.this,  t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Callgettop5(){
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);


        Call<ArrayList<Novel>> call = apiService.Getlist5("");
        call.enqueue(new Callback<ArrayList<Novel>>() {
            @Override
            public void onResponse(Call<ArrayList<Novel>> call, Response<ArrayList<Novel>> response) {
                if (response.isSuccessful()) {
                    lstnv5 = new ArrayList<>();
                    // Phản hồi từ máy chủ thành công
                    lstnv5 = response.body();

                    // Sử dụng dữ liệu ở đây
                    // Ví dụ: categoryadapter.setData(lstnv);
                    lst.add(new Category("Top 5 được đánh giá cao",lstnv5));
                    categoryadapter.setData(lst);
                    rv.setAdapter(categoryadapter);
                } else {
                    // Xử lý khi có phản hồi không thành công
                    // Ví dụ: Hiển thị thông báo lỗi
                    Toast.makeText(HomeAct.this, "Không thể nhận dữ liệu từ máy chủ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Novel>> call, Throwable t) {
            // Xử lý khi có lỗi kết nối
                Toast.makeText(HomeAct.this,  t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getUser(String username){
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<Account> call = apiService.Getuser(username);
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Account a = response.body();
                    getlastseeninfor(a.getId());
                    //Tạo biến toàn cục
                    //gán dữ liệu
                    account.setId(a.getId());
                    account.setUsername(a.getUsername());
                    //Cập nhập tên
                    useracc.setText(a.getUsername());
                }else{
                    // Ví dụ: Hiển thị thông báo lỗi
                    Toast.makeText(HomeAct.this, "Không thể nhận dữ liệu từ máy chủ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                // Xử lý khi có lỗi kết nối
                Toast.makeText(HomeAct.this,  t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getresearch(String str){

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ArrayList<Novel>> call = apiService.GetlistSearch(str);
        call.enqueue(new Callback<ArrayList<Novel>>() {
            @Override
            public void onResponse(Call<ArrayList<Novel>> call, Response<ArrayList<Novel>> response) {
                if(response.isSuccessful()){
                    if(response.body()==null){

                    }else{
                        ResearchAdapter researchAdapter = new ResearchAdapter(HomeAct.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeAct.this, RecyclerView.VERTICAL, false);
                        rvresearch.setLayoutManager(linearLayoutManager);

                        lstresearch = response.body();
                        researchAdapter.setData(lstresearch);
                        rvresearch.setAdapter(researchAdapter);
                    }



                }else{
                    Toast.makeText(HomeAct.this, "Không thể nhận dữ liệu từ máy chủ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Novel>> call, Throwable t) {
                Toast.makeText(HomeAct.this,  t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });


    }
}
