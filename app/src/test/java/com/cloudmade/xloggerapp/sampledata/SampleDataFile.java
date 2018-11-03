package com.cloudmade.xloggerapp.sampledata;

import com.cloudmade.xlogger.ObservableBooleanWrapper;
import com.cloudmade.xlogger.ObservableByteWrapper;
import com.cloudmade.xlogger.ObservableCharWrapper;
import com.cloudmade.xlogger.ObservableDoubleWrapper;
import com.cloudmade.xlogger.ObservableFieldWrapper;
import com.cloudmade.xlogger.ObservableFloatWrapper;
import com.cloudmade.xlogger.ObservableIntWrapper;
import com.cloudmade.xlogger.ObservableLongWrapper;
import com.cloudmade.xlogger.ObservableShortWrapper;
import com.cloudmade.xlogger.ViewModelMainActivitySampleInitializer;

public enum SampleDataFile {

    BOOLEAN_WRAPPER("observable_boolean_wrapper_sample.txt", ObservableBooleanWrapper.class),
    BYTE_WRAPPER("observable_byte_wrapper_sample.txt", ObservableByteWrapper.class),
    CHAR_WRAPPER("observable_char_wrapper_sample.txt", ObservableCharWrapper.class),
    DOUBLE_WRAPPER("observable_double_wrapper_sample.txt", ObservableDoubleWrapper.class),
    FLOAT_WRAPPER("observable_float_wrapper_sample.txt", ObservableFloatWrapper.class),
    INT_WRAPPER("observable_int_wrapper_sample.txt", ObservableIntWrapper.class),
    LONG_WRAPPER("observable_long_wrapper_sample.txt", ObservableLongWrapper.class),
    SHORT_WRAPPER("observable_short_wrapper_sample.txt", ObservableShortWrapper.class),
    FIELD_WRAPPER("observable_field_wrapper_sample.txt", ObservableFieldWrapper.class),
    LOG_INITIALIZER("view_model_main_activity_sample_initializer_sample.txt", ViewModelMainActivitySampleInitializer.class);

    public final String expectedContentPath;
    public final Class generatedClass;

    SampleDataFile(String expectedContentFileName, Class generatedClass) {
        this.expectedContentPath = expectedContentFileName;
        this.generatedClass = generatedClass;
    }
}
