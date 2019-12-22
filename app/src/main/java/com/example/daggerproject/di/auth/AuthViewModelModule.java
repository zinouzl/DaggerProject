package com.example.daggerproject.di.auth;


import androidx.lifecycle.ViewModel;

import com.example.daggerproject.di.ViewModelKey;
import com.example.daggerproject.ui.auth.AuthViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindViewModel(AuthViewModel authViewModel);

}
