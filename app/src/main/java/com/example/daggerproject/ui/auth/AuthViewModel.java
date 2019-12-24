package com.example.daggerproject.ui.auth;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.RequestManager;
import com.example.daggerproject.SessionManager;
import com.example.daggerproject.model.User;

import javax.inject.Inject;

public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";
    @Inject
    RequestManager requestManager;
    @Inject
    Drawable appDrawable;
    private SessionManager sessionManager;


    @Inject
    public AuthViewModel(SessionManager sessionManager) {
        super();

        this.sessionManager = sessionManager;

    }

    public void authWithId(int id) {
        sessionManager.authenticateWithId(sessionManager.getUser(id));

    }


    public LiveData<AuthResources<User>> observeAuthentification() {
        return sessionManager.getAuthData();

    }

    public void setActivityLogo(ImageView imageView) {
        requestManager
                .load(appDrawable)
                .into(imageView);
    }
}
