package com.example.daggerproject.retrofit.auth;

import com.example.daggerproject.model.User;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AuthApi {


    @GET("Users/{id}")
    Flowable<User> getUser(@Path("id") int id);
}
