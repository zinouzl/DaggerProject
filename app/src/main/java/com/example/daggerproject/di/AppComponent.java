package com.example.daggerproject.di;


import android.app.Application;

import com.example.daggerproject.TheBaseApp;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(
        modules = {
                AndroidSupportInjectionModule.class

        }
)
public interface AppComponent extends AndroidInjector<TheBaseApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);


        AppComponent build();
    }
}
