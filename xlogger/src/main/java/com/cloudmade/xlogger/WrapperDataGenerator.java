package com.cloudmade.xlogger;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;

class WrapperDataGenerator {

    private ProcessingEnvironment processingEnvironment;
    private VelocityEngine velocityEngine;

    private List<String> alreadyCreatedWrappers;

    WrapperDataGenerator(ProcessingEnvironment processingEnvironment, VelocityEngine velocityEngine) {
        this.processingEnvironment = processingEnvironment;
        this.velocityEngine = velocityEngine;

        this.alreadyCreatedWrappers = new ArrayList<>();
    }

    /**
     * Generates wrapper classes
     * @param annotatedElementEntities list with info about annotated elements (field name and wrapper)
     */
    void generateWrappers(List<AnnotatedElementEntity> annotatedElementEntities) {
        for (AnnotatedElementEntity annotatedElementEntity : annotatedElementEntities) {
            if (alreadyCreatedWrappers.isEmpty() || !alreadyCreatedWrappers.contains(annotatedElementEntity.getWrapperData().wrapperShortName)) {
                createWrapper(annotatedElementEntity.getWrapperData());
            }
        }
    }

    /**
     * Generates wrapper class with overridden method set(value) which logs value which is set
     * @param wrapperData info about loggable field type
     */
    private void createWrapper(WrapperData wrapperData) {
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("package", Const.GENERATED_PACKAGE);
        velocityContext.put("sourceClass", wrapperData.superClassShortName);
        velocityContext.put("generic", wrapperData.isPrimitive ? "" : "<T>");
        velocityContext.put("resultClassName", wrapperData.wrapperShortName);
        velocityContext.put("valueType", wrapperData.isPrimitive ? wrapperData.valueTypeName : "T");

        Template template = velocityEngine.getTemplate(VelocityTemplate.OBSERVABLE_WRAPPER.templatePath);

        try {
            JavaFileObject source = processingEnvironment.getFiler().createSourceFile(wrapperData.wrapperFullName);
            Writer writer = source.openWriter();
            template.merge(velocityContext, writer);
            writer.close();
            alreadyCreatedWrappers.add(wrapperData.wrapperShortName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
