package com.example.daggerproject.di;


import com.example.daggerproject.di.auth.AuthViewModelModule;
import com.example.daggerproject.ui.AuthActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = {
            AuthViewModelModule.class
    })
    abstract AuthActivity contributeAuthActivity();

}
