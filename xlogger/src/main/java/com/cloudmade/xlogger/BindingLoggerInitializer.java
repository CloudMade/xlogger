package com.cloudmade.xlogger;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;

class BindingLoggerInitializer {

    private ProcessingEnvironment processingEnvironment;
    private VelocityEngine velocityEngine;

    BindingLoggerInitializer(ProcessingEnvironment processingEnvironment, VelocityEngine velocityEngine) {
        this.processingEnvironment = processingEnvironment;
        this.velocityEngine = velocityEngine;
    }

    /**
     * Generates classes for single class with fields annotated with {@link Loggable}
     * @param enclosingClassName name of class which contains fields annotated with {@link Loggable}
     * @param annotatedElementEntities info about annotated field
     */
    void createLogInitializer(String enclosingClassName, List<AnnotatedElementEntity> annotatedElementEntities) {
        VelocityContext velocityContext = new VelocityContext();

        String simpleEnclosingClassName = enclosingClassName.substring(enclosingClassName.lastIndexOf('.') + 1);
        String fileName = simpleEnclosingClassName + Const.INITIALIZER_POST_FIX;

        velocityContext.put("package", Const.GENERATED_PACKAGE);
        velocityContext.put("initClassName", fileName);
        velocityContext.put("enclosingClassFullName", enclosingClassName);

        //create body of static method initXLogger(enclosingClass)
        velocityContext.put("body", createFieldReinitializers(annotatedElementEntities));

        Template template = velocityEngine.getTemplate(VelocityTemplate.INIT_CLASS.templatePath);

        try {
            //write java file with static method initXLogger(enclosingClass) which
            //should be called from enclosing class with loggable observable fields
            JavaFileObject source = processingEnvironment.getFiler().createSourceFile(Const.GENERATED_PACKAGE + "." + fileName);
            Writer writer = source.openWriter();
            template.merge(velocityContext, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates body of static method initXLogger(enclosingClass)
     * @param annotatedElementEntities info about annotated field
     * @return body of static method initXLogger(enclosingClass)
     */
    private String createFieldReinitializers(List<AnnotatedElementEntity> annotatedElementEntities) {
        StringBuilder methodContent = new StringBuilder();
        for (AnnotatedElementEntity element : annotatedElementEntities) {
            methodContent.append(getFieldReinitializeCodeSnippet(element.getWrapperData(), element.getFieldName())).append("\n");
        }
        return methodContent.toString();
    }

    /**
     * Creates reinitialize content for single field annotated with {@link Loggable}
     * @param wrapperData info about annotated field
     * @param simpleName name of the field annotated with {@link Loggable}
     * @return reinitialize content for single field annotated with {@link Loggable}
     */
    private String getFieldReinitializeCodeSnippet(WrapperData wrapperData, String simpleName) {
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("fullObservableWrapperName", wrapperData.wrapperFullName);
        velocityContext.put("genericType", wrapperData.isPrimitive ? "" : wrapperData.genericParam);
        velocityContext.put("fieldName", simpleName);

        Template template = velocityEngine.getTemplate(VelocityTemplate.INIT_FIELD.templatePath);
        StringWriter sw = new StringWriter();
        template.merge(velocityContext, sw);
        return sw.toString();
    }
}
