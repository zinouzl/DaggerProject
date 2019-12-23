package com.example.daggerproject.ui.main.posts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daggerproject.R;
import com.example.daggerproject.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PostsFragment extends DaggerFragment {

    private PostsViewModel mViewModel;
    @Inject
    private ViewModelProviderFactory viewModelProviderFactory;
    private RecyclerView recyclerView;

    public static PostsFragment newInstance() {
        return new PostsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(PostsViewModel.class);
        // TODO: Use the ViewModel
        recyclerView = view.findViewById(R.id.post_recycler_view);
    }

}
