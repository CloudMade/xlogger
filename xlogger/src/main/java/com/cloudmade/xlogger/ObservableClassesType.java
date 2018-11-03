package com.cloudmade.xlogger;


import java.util.HashMap;
import java.util.Map;

class ObservableClassesType {

    private ObservableClassesType() {}

    static final Map<String, String> TYPE_CONNECTIONS;

    static {
        TYPE_CONNECTIONS = new HashMap<>();
        TYPE_CONNECTIONS.put("ObservableBoolean", "boolean");
        TYPE_CONNECTIONS.put("ObservableByte", "byte");
        TYPE_CONNECTIONS.put("ObservableChar", "char");
        TYPE_CONNECTIONS.put("ObservableDouble", "double");
        TYPE_CONNECTIONS.put("ObservableFloat", "float");
        TYPE_CONNECTIONS.put("ObservableInt", "int");
        TYPE_CONNECTIONS.put("ObservableLong", "long");
        TYPE_CONNECTIONS.put("ObservableShort", "short");
    }
}
