package com.example.daggerproject.ui;

import androidx.lifecycle.ViewModel;

import com.example.daggerproject.retrofit.auth.AuthApi;

import javax.inject.Inject;

public class AuthViewModel extends ViewModel {

    private final AuthApi authApi;
    @Inject
    public AuthViewModel(AuthApi authApi) {
        super();
        this.authApi = authApi;

    }
}
