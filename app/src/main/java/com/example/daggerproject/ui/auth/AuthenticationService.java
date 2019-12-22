package com.example.daggerproject.ui.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class AuthenticationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        AccountAuthenticator authenticator = new AccountAuthenticator(this);
        return authenticator.getIBinder();
    }
}
