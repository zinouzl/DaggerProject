package com.example.daggerproject;

import android.content.Intent;
import android.os.Bundle;

import com.example.daggerproject.ui.auth.AuthActivity;
import com.example.daggerproject.ui.main.MainActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class SplashActivity extends DaggerAppCompatActivity {
    @Inject
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (sessionManager.getAccount() == null)
            startActivity(new Intent(this, AuthActivity.class));
        else {
            startActivity(new Intent(this, MainActivity.class));
        }


        finish();

    }


}
