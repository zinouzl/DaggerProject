package com.example.daggerproject.ui;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.lifecycle.ViewModelProviders;

import com.example.daggerproject.R;
import com.example.daggerproject.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity {

    private AuthViewModel viewModel;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        viewModel = ViewModelProviders.of(this,viewModelProviderFactory).get(AuthViewModel.class);
        viewModel.setActivityLogo((ImageView) findViewById(R.id.image_view));
    }



}
