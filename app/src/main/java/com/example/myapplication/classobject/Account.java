package com.example.myapplication.classobject;

import com.google.gson.annotations.SerializedName;

public class Account {
    @SerializedName("ID")
    private int id;
    @SerializedName("Useracc")
    private String username;
    @SerializedName("Email")
    private String email;
    @SerializedName("pass")
    private String passwood;

//    Singleton Support

    private static  Account instance;
    private Account() {
    }

    public static Account getInstance(){
        if (instance == null){
            instance = new Account();
        }
        return  instance;
    }

// Normal Object code



    public Account(String username, String passwood) {
        this.username = username;
        this.passwood = passwood;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswood() {
        return passwood;
    }

    public void setPasswood(String passwood) {
        this.passwood = passwood;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
