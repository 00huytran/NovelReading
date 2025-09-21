package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.api.AccountExitResponse;
import com.example.myapplication.api.ApiService;
import com.example.myapplication.api.ChangePassResponse;
import com.example.myapplication.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Forgotpass extends AppCompatActivity {
    private TextView user,pass,repass;
    private Button getuser,changepass;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);

        user = findViewById(R.id.editTextTextAccReset);
        pass = findViewById(R.id.editTextTextNewPassword1);
        repass = findViewById(R.id.editTextTextReNewPassword);

        getuser = findViewById(R.id.buttonfindacc);
        changepass = findViewById(R.id.buttonchangePassword);

        builder = new AlertDialog.Builder((this));

        Button goback = findViewById(R.id.buttonfindaccback);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        getuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!user.getText().equals("") ) {
                    getCheckUser(user.getText().toString());
                }
            }
        });
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pass.getText().toString().equals(repass.getText().toString())) setChangepass(user.getText().toString(),pass.getText().toString());
                else Toast.makeText(Forgotpass.this, "pass nhập lại không giống", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        pass.setVisibility(View.GONE);
        repass.setVisibility(View.GONE);
        changepass.setVisibility(View.GONE);
    }

    public void getCheckUser(String username){
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<AccountExitResponse> call = apiService.IsaccExit(username);
        call.enqueue(new Callback<AccountExitResponse>() {
            @Override
            public void onResponse(Call<AccountExitResponse> call, Response<AccountExitResponse> response) {
                if(response.isSuccessful()){
                    AccountExitResponse accountExitResponse = response.body();
                    if(accountExitResponse.isCheck()) {
                        pass.setVisibility(View.VISIBLE);
                        repass.setVisibility(View.VISIBLE);
                        changepass.setVisibility(View.VISIBLE);
                    }else{
                        pass.setVisibility(View.GONE);
                        repass.setVisibility(View.GONE);
                        changepass.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<AccountExitResponse> call, Throwable t) {

            }
        });
    }
    public void setChangepass(String username,String pass){
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ChangePassResponse> call = apiService.changePassmess(username,pass);
        call.enqueue(new Callback<ChangePassResponse>() {
            @Override
            public void onResponse(Call<ChangePassResponse> call, Response<ChangePassResponse> response) {
                if (response.isSuccessful()) {
                    ChangePassResponse changePassResponse = response.body();
                    if(changePassResponse.isSuccess()) {
                        Toast.makeText(Forgotpass.this, changePassResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{

                            builder.setTitle("ERROR").setMessage(changePassResponse.getMessage()).setCancelable(true).setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                    }
                }else  Toast.makeText(Forgotpass.this, "api không phản hồi lại", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<ChangePassResponse> call, Throwable t) {
                Toast.makeText(Forgotpass.this, "Server error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}