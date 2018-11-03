package com.cloudmade.xloggerapp;

import android.app.Application;

import com.cloudmade.xloggerapp.di.ApplicationComponent;
import com.cloudmade.xloggerapp.di.ApplicationModule;
import com.cloudmade.xloggerapp.di.DaggerApplicationComponent;

public class XLoggerApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
