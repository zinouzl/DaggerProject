package com.example.daggerproject.ui;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.lifecycle.ViewModel;

import com.bumptech.glide.RequestManager;
import com.example.daggerproject.model.User;
import com.example.daggerproject.retrofit.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";

    private final AuthApi authApi;
    @Inject
    RequestManager requestManager;

    @Inject
    Drawable appDrawable;


    @Inject
    public AuthViewModel(AuthApi authApi) {
        super();
        this.authApi = authApi;

        this.authApi.getUser(1)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User value) {

                        Log.d(TAG, "onNext: "+ value.getEmail());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+ e.toString());

                    }

                    @Override
                    public void onComplete() {

                    }
                });



    }
    public void setActivityLogo(ImageView imageView){
        requestManager
                .load(appDrawable)
                .into(imageView);
    }
}
