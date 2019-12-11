package com.example.daggerproject.ui;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.RequestManager;
import com.example.daggerproject.SessionManager;
import com.example.daggerproject.model.User;
import com.example.daggerproject.retrofit.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";

    private final AuthApi authApi;

    private SessionManager sessionManager;

    @Inject
    RequestManager requestManager;

    @Inject
    Drawable appDrawable;


    @Inject
    public AuthViewModel(AuthApi authApi, SessionManager sessionManager) {
        super();
        this.authApi = authApi;
        this.sessionManager = sessionManager;

    }

    public void authWithId(int id) {
        sessionManager.authenticateWithId(getUser(id));

    }


    private LiveData<AuthResources<User>> getUser(int id) {
        return LiveDataReactiveStreams.fromPublisher(
                authApi.getUser(id)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(new Function<Throwable, User>() {
                            @Override
                            public User apply(Throwable throwable) throws Exception {
                                User nullUser = new User();
                                nullUser.setId(-1);
                                return nullUser;
                            }
                        })
                        .map(new Function<User, AuthResources<User>>() {
                            @Override
                            public AuthResources<User> apply(User user) throws Exception {
                                if (user.getId() == -1) {
                                    return AuthResources.error("Could not authenticate", null);
                                }
                                return AuthResources.authenticated(user);
                            }
                        })

        );
    }

    public LiveData<AuthResources<User>> observeAuthentification() {
        return sessionManager.getAuthData();

    }
    public void setActivityLogo(ImageView imageView){
        requestManager
                .load(appDrawable)
                .into(imageView);
    }
}
