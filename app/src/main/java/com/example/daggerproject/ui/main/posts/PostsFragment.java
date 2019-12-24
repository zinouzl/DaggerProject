package com.example.daggerproject.ui.main.posts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daggerproject.R;
import com.example.daggerproject.model.Post;
import com.example.daggerproject.model.User;
import com.example.daggerproject.recycler_view.PostAdapter;
import com.example.daggerproject.ui.auth.AuthResources;
import com.example.daggerproject.ui.main.Resource;
import com.example.daggerproject.viewmodel.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PostsFragment extends DaggerFragment {

    private static final String TAG = "PostsFragment";
    private PostsViewModel mViewModel;
    @Inject
    ViewModelProviderFactory viewModelProviderFactory;
    @Inject
    PostAdapter postAdapter;



    private RecyclerView recyclerView;

    public static PostsFragment newInstance() {
        return new PostsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_posts, container, false);
        mViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(PostsViewModel.class);

        recyclerView = v.findViewById(R.id.post_recycler_view);
        subscribeObservers();
        subscribeUserObservers();
        initRecyclerView();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


    }


    private void subscribeObservers() {
        mViewModel.getPosts().removeObservers(getViewLifecycleOwner());
        mViewModel.getPosts().observe(getViewLifecycleOwner(), new Observer<Resource<List<Post>>>() {
            @Override
            public void onChanged(Resource<List<Post>> listResource) {
                Log.d(TAG, "onChanged: " + listResource.status);
                switch (listResource.status) {
                    case ERROR: {
                        Log.d(TAG, "onChanged: on error ");
                        break;
                    }
                    case LOADING: {
                        Log.d(TAG, "onChanged: is loading");
                        break;
                    }
                    case SUCCESS: {
                        Log.d(TAG, "onChanged: " + listResource.data);
                        postAdapter.setPosts(listResource.data);
                        break;
                    }
                }
            }
        });
    }


    private void subscribeUserObservers() {
        mViewModel.getAuthenticatedUser().removeObservers(getViewLifecycleOwner());
        mViewModel.getAuthenticatedUser().observe(getViewLifecycleOwner(), new Observer<AuthResources<User>>() {
            @Override
            public void onChanged(AuthResources<User> userAuthResources) {
                if (userAuthResources != null) {
                    switch (userAuthResources.status) {
                        case AUTHENTICATED: {
                            setUserData(userAuthResources.data);
                            break;
                        }
                        case ERROR: {
                            setErrorMessage(userAuthResources.message);
                            break;
                        }

                    }

                }
            }

            private void setErrorMessage(String message) {

            }

            private void setUserData(User data) {


            }
        });
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(postAdapter);
    }

}
