package com.cloudmade.xloggerapp.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cloudmade.xloggerapp.R;
import com.cloudmade.xloggerapp.XLoggerApplication;
import com.cloudmade.xloggerapp.databinding.BindingMainActivity;
import com.cloudmade.xloggerapp.di.ActivityComponent;
import com.cloudmade.xloggerapp.di.ActivityModule;
import com.cloudmade.xloggerapp.di.ApplicationComponent;
import com.cloudmade.xloggerapp.viewmodel.ViewModelMainActivity;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    ViewModelMainActivity viewModelMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindingMainActivity binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        ApplicationComponent applicationComponent = ((XLoggerApplication) getApplication()).getApplicationComponent();
        ActivityComponent activityComponent = applicationComponent.plus(new ActivityModule(this));
        activityComponent.inject(this);
        activityComponent.inject(viewModelMainActivity);

        binding.setViewModel(viewModelMainActivity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModelMainActivity.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModelMainActivity.onPause();
    }
}
