package com.example.daggerproject.di;


import com.example.daggerproject.SplashActivity;
import com.example.daggerproject.di.auth.AuthModule;
import com.example.daggerproject.di.auth.AuthViewModelModule;
import com.example.daggerproject.di.main.MainFragmentBuilderModule;
import com.example.daggerproject.di.main.MainModule;
import com.example.daggerproject.di.main.MainViewModelModule;
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


    @ContributesAndroidInjector(modules = {
            MainFragmentBuilderModule.class,
            MainViewModelModule.class,
            MainModule.class
    })
    abstract MainActivity contributeMainActivity();
}
