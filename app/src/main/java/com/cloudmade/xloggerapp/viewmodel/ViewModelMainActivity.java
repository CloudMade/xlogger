package com.cloudmade.xloggerapp.viewmodel;

import android.databinding.ObservableField;

import com.cloudmade.xlogger.Loggable;

import static com.cloudmade.xlogger.ViewModelMainActivityInitializer.initXLogger;

public class ViewModelMainActivity extends BaseViewModel {

    @Loggable
    public ObservableField<String> messageObservable = new ObservableField<>();

    public ViewModelMainActivity() {
        initXLogger(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        messageObservable.set("Hello World!");
    }
}
