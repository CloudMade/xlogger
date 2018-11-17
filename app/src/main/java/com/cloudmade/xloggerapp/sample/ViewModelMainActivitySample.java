package com.cloudmade.xloggerapp.sample;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableByte;
import android.databinding.ObservableChar;
import android.databinding.ObservableDouble;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.databinding.ObservableInt;
import android.databinding.ObservableLong;
import android.databinding.ObservableShort;

import com.cloudmade.xlogger.Loggable;
import com.cloudmade.xlogger.XLogger;
import com.cloudmade.xloggerapp.viewmodel.BaseViewModel;

public class ViewModelMainActivitySample extends BaseViewModel {

    @Loggable
    public ObservableField<String> observableFieldString = new ObservableField<>();
    @Loggable
    public ObservableBoolean observableBoolean = new ObservableBoolean();
    @Loggable
    public ObservableByte observableByte = new ObservableByte();
    @Loggable
    public ObservableChar observableChar = new ObservableChar();
    @Loggable
    public ObservableDouble observableDouble = new ObservableDouble();
    @Loggable
    public ObservableFloat observableFloat = new ObservableFloat();
    @Loggable
    public ObservableInt observableInt = new ObservableInt();
    @Loggable
    public ObservableLong observableLong = new ObservableLong();
    @Loggable
    public ObservableShort observableShort = new ObservableShort();

    public ViewModelMainActivitySample() {
        XLogger.init(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        observableFieldString.set("Hello World!");
        observableBoolean.set(true);
        observableByte.set((byte) 1);
        observableChar.set('r');
        observableDouble.set(120);
        observableFloat.set(90);
        observableInt.set(20);
        observableLong.set(500);
        observableShort.set((short) 20);
    }
}
