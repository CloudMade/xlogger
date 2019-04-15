package com.cloudmade.xloggerapp.viewmodel;

import com.cloudmade.xlogger.Loggable;
import com.cloudmade.xlogger.XLogger;

import androidx.lifecycle.MutableLiveData;

public class ViewModelMainActivity extends BaseViewModel {

    @Loggable
    public MutableLiveData<String> messageLiveData = new MutableLiveData<>();

    public ViewModelMainActivity() {
        XLogger.init(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        messageLiveData.setValue("Hello World!");
    }
}
