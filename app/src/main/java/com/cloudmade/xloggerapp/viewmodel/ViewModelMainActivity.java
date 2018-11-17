package com.cloudmade.xloggerapp.viewmodel;

import android.databinding.ObservableField;

import com.cloudmade.xlogger.Loggable;
import com.cloudmade.xlogger.XLogger;

public class ViewModelMainActivity extends BaseViewModel {

    @Loggable
    public ObservableField<String> messageObservable = new ObservableField<>();

    public ViewModelMainActivity() {
        XLogger.init(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        messageObservable.set("Hello World!");
    }
}
