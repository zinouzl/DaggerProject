package com.example.daggerproject.retrofit.main;

import com.example.daggerproject.model.Post;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainApi {


    @GET("posts")
    Flowable<List<Post>> getPostsOfUser(@Query("userId") int userId);
}
