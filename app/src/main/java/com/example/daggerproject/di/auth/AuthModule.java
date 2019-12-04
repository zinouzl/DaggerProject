package com.example.daggerproject.di.auth;


import com.example.daggerproject.retrofit.auth.AuthApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class AuthModule {

    @Provides
    static AuthApi provideAuthApi(Retrofit retrofit) {
        return retrofit.create(AuthApi.class);
    }

}
