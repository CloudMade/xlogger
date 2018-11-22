# XLogger

**XLogger** is a Java library built for Android which writes to **Logcat** when new value is set to **Android Data Binding Observable Field**.

### Supported Data Binding Observable Fields:
* **ObservableBoolean**
* **ObservableByte**
* **ObservableChar**
* **ObservableDouble**
* **ObservableFloat**
* **ObservableInt**
* **ObservableLong**
* **ObservableShort**
* **ObservableField\<T\>**

In order to see the message in **Logcat** in a readable format when setting new value to **ObservableField\<T\>** `Object.toString()` should be overriden in **T**.

To make **Data Binding Observable Field Loggable**, it should be annotated with `@Loggable` and in enclosing class generated static method `initXLogger(this);` should be called.

### Example:

    @Loggable
    public ObservableField<String> messageObservable = new ObservableField<>();
    
Call generated static method `XLogger.init(this);`

Set new value to **Observable Field**.
`messageObservable.set("Hello World!");`

And in **Logcat** message can be seen:
`10388-10388/com.cloudmade.xloggerapp D/UI: value Hello World! was set into ViewModelMainActivity.messageObservable`

### Installation:

    implementation 'com.cloudmade.xlogger:xlogger:1.0.0'
    annotationProcessor 'com.cloudmade.xlogger:xlogger:1.0.0'



