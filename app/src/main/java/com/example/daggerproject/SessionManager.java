package com.example.daggerproject;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.daggerproject.model.User;
import com.example.daggerproject.ui.AuthResources;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {

    private static final String TAG = "SessionManager";

    private MediatorLiveData<AuthResources<User>> cacheUser = new MediatorLiveData<>();

    @Inject
    public SessionManager() {
    }

    public void authenticateWithId(final LiveData<AuthResources<User>> source) {
        if (cacheUser != null) {
            cacheUser.setValue(AuthResources.loading((User) null));
            cacheUser.addSource(source, new Observer<AuthResources<User>>() {
                @Override
                public void onChanged(AuthResources<User> userAuthResources) {
                    cacheUser.setValue(userAuthResources);
                    cacheUser.removeSource(source);
                }
            });
        }
    }


    public void logOut() {
        cacheUser.setValue(AuthResources.<User>notAuthenticated());
    }


    public LiveData<AuthResources<User>> getAuthData() {
        return cacheUser;
    }
}
