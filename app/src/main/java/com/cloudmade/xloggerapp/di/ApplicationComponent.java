package com.cloudmade.xloggerapp.di;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    ActivityComponent plus(ActivityModule activityModule);
}
