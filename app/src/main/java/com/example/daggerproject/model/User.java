package com.example.daggerproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {


    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("username")
    @Expose
    private String userName;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("website")
    @Expose
    private String website;

    public User(int id, String userName, String email, String website) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.website = website;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getWebsite() {
        return website;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
