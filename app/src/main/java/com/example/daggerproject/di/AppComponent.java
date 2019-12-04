package com.example.daggerproject.di;


import android.app.Application;

import com.example.daggerproject.TheBaseApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuilderModule.class,
                AppModule.class

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
