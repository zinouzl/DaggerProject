package com.example.daggerproject.ui.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.daggerproject.R;
import com.example.daggerproject.model.User;
import com.example.daggerproject.ui.main.MainActivity;
import com.example.daggerproject.viewmodel.ViewModelProviderFactory;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity {

    private AuthViewModel viewModel;

    private static final String TAG = "AuthActivity";
    TextInputEditText editText;

    MaterialButton button;

    ProgressBar
            progressBar;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(AuthViewModel.class);
        viewModel.setActivityLogo((ImageView) findViewById(R.id.image_view));

        editText = findViewById(R.id.text_field);
        button = findViewById(R.id.button);
        progressBar = findViewById(R.id.progress_circular);
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
            hideKeyboard(this);
            viewModel.authWithId(Integer.parseInt(editText.getText().toString()));
        }

    }


    private void subsctibeObserver() {
        viewModel.observeAuthentification().observe(this, new Observer<AuthResources<User>>() {
            @Override
            public void onChanged(AuthResources<User> userAuthResources) {
                if (userAuthResources != null) {
                    Log.d(TAG, "onChanged: is being executed");
                    switch (userAuthResources.status) {

                        case LOADING: {
                            showProgressBar(true);
                            break;
                        }
                        case AUTHENTICATED: {
                            showProgressBar(false);
                            Log.d(TAG, "onChanged: login success " + userAuthResources.data.getUserName());
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            break;
                        }
                        case ERROR: {
                            showProgressBar(false);
                            Snackbar.make(button.getRootView(), "encountered a problem", Snackbar.LENGTH_SHORT).show();
                            break;
                        }
                        case NOT_AUTHENTICATED: {
                            showProgressBar(false);
                            break;
                        }
                    }
                }
            }
        });


    }


    public void showProgressBar(boolean isVisible) {
        if (isVisible) {
            Log.d(TAG, "showProgressBar: True");
            ;
            progressBar.setVisibility(ProgressBar.VISIBLE);

        } else {

            progressBar.setVisibility(ProgressBar.INVISIBLE);
        }
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }

        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
