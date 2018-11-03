package com.cloudmade.xloggerapp.di;

import com.cloudmade.xloggerapp.ui.MainActivity;
import com.cloudmade.xloggerapp.viewmodel.ViewModelMainActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private MainActivity activity;

    public ActivityModule(MainActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    ViewModelMainActivity viewModelMainActivity() {
        return new ViewModelMainActivity();
    }
}
