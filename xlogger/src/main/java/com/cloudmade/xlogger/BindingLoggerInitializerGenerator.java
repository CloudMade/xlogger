package com.cloudmade.xlogger;

import org.apache.velocity.VelocityContext;

import java.util.List;

class BindingLoggerInitializerGenerator {

    private ClassGenerator classGenerator;

    BindingLoggerInitializerGenerator(ClassGenerator classGenerator) {
        this.classGenerator = classGenerator;
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

        classGenerator.writeFile(Const.GENERATED_PACKAGE + "." + fileName, VelocityTemplate.INIT_CLASS, velocityContext);
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

        return classGenerator.mergeVelocityContext(velocityContext, VelocityTemplate.INIT_FIELD);
    }
}
