package com.example.daggerproject.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.daggerproject.R;
import com.example.daggerproject.model.User;
import com.example.daggerproject.viewmodel.ViewModelProviderFactory;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity {

    private AuthViewModel viewModel;

    private static final String TAG = "AuthActivity";
    TextInputEditText editText;

    MaterialButton button;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        viewModel = ViewModelProviders.of(this,viewModelProviderFactory).get(AuthViewModel.class);
        viewModel.setActivityLogo((ImageView) findViewById(R.id.image_view));

        editText = findViewById(R.id.text_field);
        button = findViewById(R.id.button);
        subsctibeObserver();
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                loginAttempt();
                break;
            default:
                break;
        }
    }

    private void loginAttempt() {

        if (TextUtils.isEmpty(editText.getText().toString())) {
            return;
        } else {
            viewModel.authWithId(Integer.parseInt(editText.getText().toString()));
        }

    }


    private void subsctibeObserver() {
        viewModel.obserUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    Log.d(TAG, "onChanged: " + user.getEmail());
                }

            }
        });
    }


}
