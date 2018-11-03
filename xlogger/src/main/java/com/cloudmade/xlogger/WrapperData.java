package com.cloudmade.xlogger;

public class WrapperData {
    final String superClassShortName;
    final String wrapperFullName;
    final String wrapperShortName;
    final String valueTypeName;
    final boolean isPrimitive;
    final String genericParam;

    WrapperData(String superClassShortName, String wrapperFullName, String wrapperShortName, String valueTypeName, boolean isPrimitive) {
        this.superClassShortName = superClassShortName;
        this.wrapperFullName = wrapperFullName;
        this.wrapperShortName = wrapperShortName;
        this.valueTypeName = valueTypeName;
        this.isPrimitive = isPrimitive;
        this.genericParam = isPrimitive ? "" : "<" + this.valueTypeName + ">";
    }

    @Override
    public String toString() {
        return "WrapperData{" +
                "superClassShortName='" + superClassShortName + '\'' +
                ", wrapperFullName='" + wrapperFullName + '\'' +
                ", wrapperShortName='" + wrapperShortName + '\'' +
                ", valueTypeName='" + valueTypeName + '\'' +
                ", isPrimitive=" + isPrimitive +
                ", genericParam='" + genericParam + '\'' +
                '}';
    }
}
