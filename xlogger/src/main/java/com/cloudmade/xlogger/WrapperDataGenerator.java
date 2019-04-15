package com.cloudmade.xlogger;

import org.apache.velocity.VelocityContext;

import java.util.ArrayList;
import java.util.List;

class WrapperDataGenerator {

    private List<String> alreadyCreatedWrappers;
    private ClassGenerator classGenerator;

    WrapperDataGenerator(ClassGenerator classGenerator) {
        this.classGenerator = classGenerator;
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
        velocityContext.put("sourceClass", wrapperData.superClassFullName);
        velocityContext.put("resultClassName", wrapperData.wrapperShortName);

        if (classGenerator.writeFile(wrapperData.wrapperFullName, VelocityTemplate.WRAPPER, velocityContext)) {
            alreadyCreatedWrappers.add(wrapperData.wrapperShortName);
        }
    }
}
