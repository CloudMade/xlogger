# XLogger

**XLogger** is a Java library built for Android which writes to **Logcat** when new value is set to **Android LiveData**.

To make **Android LiveData Loggable**, it should be annotated with `@Loggable` and in enclosing class generated static method `initXLogger(this);` should be called.

### Example:

    @Loggable
    public MutableLiveData<String> messageLiveData = new MutableLiveData<>();
    
Call static method `XLogger.init(this);`

Set new value to **LiveData**.
`messageLiveData.set("Hello World!");`

And in **Logcat** message can be seen:
`10388-10388/com.cloudmade.xloggerapp D/UI: value Hello World! was set into ViewModelMainActivity.messageLiveData`

### Installation:

    implementation 'com.cloudmade.xlogger:xlogger:1.2.0'
    annotationProcessor 'com.cloudmade.xlogger:xlogger:1.2.0'



