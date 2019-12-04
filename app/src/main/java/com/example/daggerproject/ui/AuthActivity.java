package com.example.daggerproject.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.example.daggerproject.R;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity {


    @Inject
    RequestManager requestManager;

    @Inject
    Drawable appDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        setActivityLogo();
    }


    private void setActivityLogo(){
        requestManager
                .load(appDrawable)
                .into((ImageView) findViewById(R.id.image_view));
    }
}
