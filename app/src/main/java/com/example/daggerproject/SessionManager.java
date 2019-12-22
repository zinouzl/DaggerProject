package com.example.daggerproject;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.daggerproject.model.User;
import com.example.daggerproject.retrofit.auth.AuthApi;
import com.example.daggerproject.ui.auth.AuthResources;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.example.daggerproject.util.AppConstants.USER_ID;

@Singleton
public class SessionManager {

    private static final String TAG = "SessionManager";
    public static final String TYPE_ACCOUNT = "com.example.daggerproject";
    @Inject
    AuthApi authApi;

    @Inject
    AccountManager accountManager;
    private int id;
    private Account account;
    private MediatorLiveData<AuthResources<User>> cacheUser = new MediatorLiveData<>();
    @Inject
    public SessionManager(AuthApi authApi, AccountManager accountManager) {
        this.accountManager = accountManager;
        this.authApi = authApi;

        Account account = null;
        try {

            account = accountManager.getAccountsByType(TYPE_ACCOUNT)[0];
            id = Integer.parseInt(accountManager.getUserData(account, USER_ID));
        } catch (Exception e) {
            e.fillInStackTrace();
            Log.d(TAG, "SessionManager: account is " + e.fillInStackTrace());
        }
        Log.d(TAG, "SessionManager: account is ");
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void authenticateWithId(final LiveData<AuthResources<User>> source) {
        if (cacheUser != null) {
            cacheUser.setValue(AuthResources.loading((User) null));
            cacheUser.addSource(source, new Observer<AuthResources<User>>() {
                @Override
                public void onChanged(AuthResources<User> userAuthResources) {
                    cacheUser.setValue(userAuthResources);
                    cacheUser.removeSource(source);
                    switch (userAuthResources.status) {
                        case AUTHENTICATED: {
                            account = new Account(cacheUser.toString(), TYPE_ACCOUNT);
                            accountManager.addAccountExplicitly(account, null, null);
                            accountManager.setUserData(account, USER_ID, String.valueOf(userAuthResources.data.getId()));
                            break;

                        }
                        default:
                            break;

                    }


                }
            });
        }
    }


    public LiveData<AuthResources<User>> getUser(int id) {
        return LiveDataReactiveStreams.fromPublisher(
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
    }

    public Account getAccount() {
        return this.account;

    }

    public void logOut() {
        cacheUser.setValue(AuthResources.<User>notAuthenticated());
        accountManager.removeAccountExplicitly(account);
        this.account = null;
    }


    public LiveData<AuthResources<User>> getAuthData() {
        return cacheUser;
    }
}
