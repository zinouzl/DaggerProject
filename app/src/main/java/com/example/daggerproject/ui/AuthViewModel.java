package com.example.daggerproject.ui;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.RequestManager;
import com.example.daggerproject.model.User;
import com.example.daggerproject.retrofit.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";

    private final AuthApi authApi;

    @Inject
    MediatorLiveData<User> authUser;

    @Inject
    RequestManager requestManager;

    @Inject
    Drawable appDrawable;


    @Inject
    public AuthViewModel(AuthApi authApi) {
        super();
        this.authApi = authApi;

    }

    public void authWithId(int id) {
        final LiveData<User> source = LiveDataReactiveStreams.fromPublisher(
                authApi.getUser(id)
                        .subscribeOn(Schedulers.io())

        );

        authUser.addSource(source, new androidx.lifecycle.Observer<User>() {
            @Override
            public void onChanged(User user) {
                authUser.setValue(user);
                authUser.removeSource(source);
            }
        });
    }

    public LiveData<User> obserUser() {
        return authUser;

    }
    public void setActivityLogo(ImageView imageView){
        requestManager
                .load(appDrawable)
                .into(imageView);
    }
}
