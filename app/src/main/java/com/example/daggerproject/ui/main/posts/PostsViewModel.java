package com.example.daggerproject.ui.main.posts;

import androidx.lifecycle.ViewModel;

import com.example.daggerproject.SessionManager;
import com.example.daggerproject.retrofit.main.MainApi;

import javax.inject.Inject;

public class PostsViewModel extends ViewModel {
    // TODO: Implement the ViewModel


    private static final String TAG = "PostsViewModel";


    private final SessionManager sessionManager;

    private final MainApi mainApi;


    @Inject
    public PostsViewModel(SessionManager sessionManager, MainApi mainApi) {
        this.mainApi = mainApi;
        this.sessionManager = sessionManager;
    }
}
