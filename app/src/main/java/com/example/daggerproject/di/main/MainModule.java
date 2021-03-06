package com.example.daggerproject.di.main;


import com.example.daggerproject.recycler_view.PostAdapter;
import com.example.daggerproject.retrofit.main.MainApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {


    @Provides
    static MainApi provideMainApi(Retrofit retrofit) {
        return retrofit.create(MainApi.class);
    }

    @Provides
    static PostAdapter providePostAdapter() {
        return new PostAdapter();
    }

}
