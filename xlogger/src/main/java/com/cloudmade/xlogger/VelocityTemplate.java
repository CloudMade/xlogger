package com.cloudmade.xlogger;

public enum VelocityTemplate {

    INIT_CLASS("template/init_class_template.vm"),
    INIT_FIELD("template/init_field_template.vm"),
    OBSERVABLE_WRAPPER("template/observable_wrapper_template.vm");

    public final String templatePath;

    VelocityTemplate(String templatePath) {
        this.templatePath = templatePath;
    }
}
