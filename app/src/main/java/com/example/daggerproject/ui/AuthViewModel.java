package com.example.daggerproject.ui;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.RequestManager;
import com.example.daggerproject.model.User;
import com.example.daggerproject.retrofit.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";

    private final AuthApi authApi;

    @Inject
    MediatorLiveData<AuthResources<User>> authUser;

    @Inject
    RequestManager requestManager;

    @Inject
    Drawable appDrawable;


    @Inject
    public AuthViewModel(AuthApi authApi) {
        super();
        this.authApi = authApi;

    }

    public void authWithId(int id) {
        this.authUser.setValue(AuthResources.loading((User) null));
        final LiveData<AuthResources<User>> source = LiveDataReactiveStreams.fromPublisher(
                authApi.getUser(id)
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn(new Function<Throwable, User>() {
                            @Override
                            public User apply(Throwable throwable) throws Exception {
                                User nullUser = new User();
                                nullUser.setId(-1);
                                return nullUser;
                            }
                        })
                        .map(new Function<User, AuthResources<User>>() {
                            @Override
                            public AuthResources<User> apply(User user) throws Exception {
                                if (user.getId() == -1) {
                                    return AuthResources.error("Could not authenticate", null);
                                }
                                return AuthResources.authenticated(user);
                            }
                        })

        );

        authUser.addSource(source, new Observer<AuthResources<User>>() {
            @Override
            public void onChanged(AuthResources<User> userAuthResources) {
                authUser.setValue(userAuthResources);
                authUser.removeSource(source);
            }
        });
    }

    public LiveData<AuthResources<User>> obserUser() {
        return authUser;

    }
    public void setActivityLogo(ImageView imageView){
        requestManager
                .load(appDrawable)
                .into(imageView);
    }
}
