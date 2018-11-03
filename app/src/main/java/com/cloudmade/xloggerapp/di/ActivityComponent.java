package com.cloudmade.xloggerapp.di;

import com.cloudmade.xloggerapp.ui.MainActivity;
import com.cloudmade.xloggerapp.viewmodel.ViewModelMainActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(ViewModelMainActivity viewModelMainActivity);
}
