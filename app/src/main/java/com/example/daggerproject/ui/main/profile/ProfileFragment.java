package com.example.daggerproject.ui.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.daggerproject.R;
import com.example.daggerproject.model.User;
import com.example.daggerproject.ui.auth.AuthResources;
import com.example.daggerproject.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ProfileFragment extends DaggerFragment {


    @Inject
    ViewModelProviderFactory viewModelProviderFactory;
    private ProfileViewModel viewModel;
    private TextView userName, email, website;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        email = v.findViewById(R.id.email);
        userName = v.findViewById(R.id.user_name);
        website = v.findViewById(R.id.web_site);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(ProfileViewModel.class);
        subscribeObservers();
    }


    private void subscribeObservers() {
        viewModel.getAuthenticatedUser().removeObservers(getViewLifecycleOwner());
        viewModel.getAuthenticatedUser().observe(getViewLifecycleOwner(), new Observer<AuthResources<User>>() {
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
                Toast.makeText(getContext(), "no internet connection", Toast.LENGTH_LONG).show();
                email.setText("error");
                userName.setText("error");
                website.setText("error");
            }

            private void setUserData(User data) {
                email.setText(data.getEmail());
                userName.setText(data.getUserName());
                website.setText(data.getWebsite());


            }
        });
    }

}
