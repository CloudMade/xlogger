package com.cloudmade.xlogger;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes({"com.cloudmade.xlogger.Loggable"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class XLoggerAnnotationProcessor extends AbstractProcessor {

    private BindingLoggerInitializer bindingLoggerInitializer;
    private WrapperDataProcessor wrapperDataProcessor;
    private WrapperDataGenerator wrapperDataGenerator;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "class");
        velocityEngine.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();

        bindingLoggerInitializer = new BindingLoggerInitializer(processingEnvironment, velocityEngine);
        wrapperDataProcessor = new WrapperDataProcessor();
        wrapperDataGenerator = new WrapperDataGenerator(processingEnvironment, velocityEngine);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Map<String, List<Element>> annotatedFields = new HashMap<>();

        //find all fields annotated with Loggable annotation
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Loggable.class)) {
            if (element.getKind() != ElementKind.FIELD) {
                return false;
            }

            String enclosingClassCanonicalName = processingEnv.getElementUtils().getBinaryName((TypeElement) element.getEnclosingElement()).toString();

            if (annotatedFields.containsKey(enclosingClassCanonicalName)) {
                annotatedFields.get(enclosingClassCanonicalName).add(element);
            } else {
                annotatedFields.put(enclosingClassCanonicalName, new ArrayList<>(Collections.singletonList(element)));
            }
        }

        //magic starts here
        for (Map.Entry<String, List<Element>> annotatedFieldsEntry : annotatedFields.entrySet()) {
            List<AnnotatedElementEntity> annotatedElementEntities = wrapperDataProcessor.process(annotatedFieldsEntry.getValue());
            wrapperDataGenerator.generateWrappers(annotatedElementEntities);
            bindingLoggerInitializer.createLogInitializer(annotatedFieldsEntry.getKey(), annotatedElementEntities);
        }

        return true;
    }
}
