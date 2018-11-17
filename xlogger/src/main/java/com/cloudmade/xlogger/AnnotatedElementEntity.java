package com.cloudmade.xlogger;

/**
 * Contains data about annotated field (name and its corresponding wrapper)
 */
class AnnotatedElementEntity {

    private String fieldName;
    private WrapperData wrapperData;

    AnnotatedElementEntity(String fieldName, WrapperData wrapperData) {
        this.fieldName = fieldName;
        this.wrapperData = wrapperData;
    }

    String getFieldName() {
        return fieldName;
    }

    WrapperData getWrapperData() {
        return wrapperData;
    }
}

