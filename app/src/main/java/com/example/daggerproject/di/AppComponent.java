package com.example.daggerproject.di;


import android.app.Application;
import android.content.Context;

import com.example.daggerproject.SessionManager;
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
                AppModule.class,
                ViewModelFactoryModule.class

        }
)
public interface AppComponent extends AndroidInjector<TheBaseApp> {

    @Singleton
    SessionManager getSessionManager();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder context(Context context);


        AppComponent build();
    }
}
