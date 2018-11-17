package com.cloudmade.xlogger;

public class XLogger {

    private static final Class<?> INITIALIZER = getEnclosingClassInitializer();

    public static void init(Object enclosingClass) {
        if (INITIALIZER != null) {
            try {
                INITIALIZER.getMethod("init", enclosingClass.getClass()).invoke(null, enclosingClass);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static Class<?> getEnclosingClassInitializer() {
        try {
            return Class.forName("com.cloudmade.xlogger.XLoggerInitializerFactory");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
