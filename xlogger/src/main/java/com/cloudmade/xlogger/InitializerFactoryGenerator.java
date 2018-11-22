package com.cloudmade.xlogger;

import org.apache.velocity.VelocityContext;

import java.util.Set;

class InitializerFactoryGenerator {

    private ClassGenerator classGenerator;

    InitializerFactoryGenerator(ClassGenerator classGenerator) {
        this.classGenerator = classGenerator;
    }

    void generateFactory(Set<String> enclosingClassNames) {
        VelocityContext velocityContext = new VelocityContext();

        velocityContext.put("package", Const.GENERATED_PACKAGE);
        velocityContext.put("initializerFactoryMethods", getInitializerFactoryMethods(enclosingClassNames));

        classGenerator.writeFile(Const.GENERATED_PACKAGE + "." + Const.INITIALIZER_FACTORY_CLASS_NAME, VelocityTemplate.INITIALIZER_FACTORY, velocityContext);
    }

    private String getInitializerFactoryMethods(Set<String> enclosingClassNames) {
        StringBuilder sb = new StringBuilder();
        for (String enclosingClassName : enclosingClassNames) {
            VelocityContext velocityContext = new VelocityContext();
            String simpleEnclosingClassName = enclosingClassName.substring(enclosingClassName.lastIndexOf('.') + 1);
            velocityContext.put("enclosingClass", enclosingClassName);
            velocityContext.put("initializerClass", Const.GENERATED_PACKAGE + "." + simpleEnclosingClassName + Const.INITIALIZER_POST_FIX);

            sb.append(classGenerator.mergeVelocityContext(velocityContext, VelocityTemplate.INITIALIZER_FACTORY_METHOD)).append("\n");
        }
        return sb.toString();
    }
}
