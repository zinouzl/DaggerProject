package com.example.daggerproject.di.main;


import com.example.daggerproject.ui.main.posts.PostsFragment;
import com.example.daggerproject.ui.main.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuilderModule {


    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();


    @ContributesAndroidInjector
    abstract PostsFragment contributePostsFragment();
}
