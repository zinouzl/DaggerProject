package com.example.daggerproject.di;


import android.accounts.AccountManager;
import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.daggerproject.R;
import com.example.daggerproject.retrofit.auth.AuthApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.daggerproject.util.AppConstants.BASE_URL;

@Module
public abstract class AppModule {



    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    static AuthApi provideAuthApi(Retrofit retrofit) {
        return retrofit.create(AuthApi.class);
    }


    @Singleton
    @Provides
    static AccountManager provideAccountManager(Context context) {
        return AccountManager.get(context);
    }

    @Singleton
    @Provides
    static RequestOptions provideRequestOptions() {
        return RequestOptions
                .placeholderOf(R.drawable.background)
                .error(R.drawable.background);

    }


    @Singleton
    @Provides
    static RequestManager provideRequestManager(Application application, RequestOptions requestOptions) {
        return Glide.with(application)
                .setDefaultRequestOptions(requestOptions);

    }
    @Singleton
    @Provides
    static Drawable provideAppDrawable(Application application) {
        return ContextCompat.getDrawable(application, R.drawable.image);
    }


}
