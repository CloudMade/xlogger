package com.cloudmade.xlogger;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.tools.JavaFileObject;

class BindingLoggerInitializer {

    private ProcessingEnvironment processingEnvironment;

    private WrapperDataInitializer wrapperDataInitializer;
    private VelocityEngine velocityEngine;

    BindingLoggerInitializer(ProcessingEnvironment processingEnvironment) {
        this.processingEnvironment = processingEnvironment;

        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "class");
        velocityEngine.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.getName());

        velocityEngine.init();

        wrapperDataInitializer = new WrapperDataInitializer(velocityEngine, processingEnvironment);
    }

    /**
     * Generates classes for single class with fields annotated with {@link Loggable}
     * @param enclosingClassName name of class which contains fields annotated with {@link Loggable}
     * @param elements fields annotated with {@link Loggable}
     */
    void createLogInitializer(String enclosingClassName, List<Element> elements) {
        VelocityContext velocityContext = new VelocityContext();

        String simpleEnclosingClassName = enclosingClassName.substring(enclosingClassName.lastIndexOf('.') + 1);
        String fileName = simpleEnclosingClassName + Const.INITIALIZER_POST_FIX;

        velocityContext.put("package", Const.GENERATED_PACKAGE);
        velocityContext.put("initClassName", fileName);
        velocityContext.put("enclosingClassFullName", enclosingClassName);

        //create body of static method initXLogger(enclosingClass)
        velocityContext.put("body", createFieldReinitializers(elements));

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
     * @param elements fields annotated with {@link Loggable}
     * @return body of static method initXLogger(enclosingClass)
     */
    private String createFieldReinitializers(List<Element> elements) {
        StringBuilder methodContent = new StringBuilder();
        for (Element element : elements) {
            String simpleName = element.getSimpleName().toString();
            String fieldType = element.asType().toString();

            WrapperData wrapperData = wrapperDataInitializer.initWrapper(fieldType, simpleName);

            methodContent.append(getFieldReinitializeCodeSnippet(wrapperData, simpleName)).append("\n");
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
