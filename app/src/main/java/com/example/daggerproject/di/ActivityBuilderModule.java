package com.example.daggerproject.di;


import com.example.daggerproject.SplashActivity;
import com.example.daggerproject.di.auth.AuthModule;
import com.example.daggerproject.di.auth.AuthViewModelModule;
import com.example.daggerproject.ui.auth.AuthActivity;
import com.example.daggerproject.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = {
            AuthViewModelModule.class,
            AuthModule.class

    })
    abstract AuthActivity contributeAuthActivity();


    @ContributesAndroidInjector()
    abstract SplashActivity contributeSplashActivity();


    @ContributesAndroidInjector()
    abstract MainActivity contributeMainActivity();
}
