package com.cloudmade.xlogger;

class WrapperData {
    final String superClassFullName;
    final String wrapperFullName;
    final String wrapperShortName;
    final String valueTypeName;

    WrapperData(String superClassFullName, String wrapperFullName, String wrapperShortName, String valueTypeName) {
        this.superClassFullName = superClassFullName;
        this.wrapperFullName = wrapperFullName;
        this.wrapperShortName = wrapperShortName;
        this.valueTypeName = valueTypeName;
    }
}
