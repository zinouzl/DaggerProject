package com.example.daggerproject.ui.main.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.daggerproject.SessionManager;
import com.example.daggerproject.model.User;
import com.example.daggerproject.ui.auth.AuthResources;

import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {

    private static final String TAG = "ProfileViewModel";
    private SessionManager sessionManager;

    @Inject
    public ProfileViewModel(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        Log.d(TAG, "ProfileViewModel: viewModel is ready");
    }


    public LiveData<AuthResources<User>> getAuthenticatedUser() {
        return sessionManager.getAuthData();
    }

}
