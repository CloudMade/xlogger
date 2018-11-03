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

class WrapperDataInitializer {

    private VelocityEngine velocityEngine;
    private ProcessingEnvironment processingEnvironment;
    private List<String> alreadyCreatedWrappers;

    WrapperDataInitializer(VelocityEngine velocityEngine, ProcessingEnvironment processingEnvironment) {
        this.velocityEngine = velocityEngine;
        this.processingEnvironment = processingEnvironment;

        alreadyCreatedWrappers = new ArrayList<>();
    }

    /**
     * Initialize wrapper for loggable field
     * @param fieldType type of databinding observable field which should be logged
     * @param simpleName name of observable field
     * @return wrapper data of loggable observable field
     */
    WrapperData initWrapper(String fieldType, String simpleName) {
        WrapperData wrapperData = createWrapperData(fieldType);

        if (alreadyCreatedWrappers.isEmpty() || !alreadyCreatedWrappers.contains(wrapperData.wrapperShortName)) {
            createWrapper(wrapperData, simpleName);
        }

        return wrapperData;
    }

    private WrapperData createWrapperData(String superClassFullName) {
        if (superClassFullName.contains("<")) {
            //ObservableField
            String superClassShortName = superClassFullName.substring(0, superClassFullName.indexOf('<'));
            superClassShortName = superClassShortName.substring(superClassShortName.lastIndexOf('.') + 1);
            String wrapperClassShortName = superClassShortName + Const.WRAPPER_POST_FIX;
            String wrapperClassFullName = Const.GENERATED_PACKAGE + "." + wrapperClassShortName;
            String valueType = superClassFullName.substring(superClassFullName.indexOf('<') + 1, superClassFullName.length() - 1);
            return new WrapperData(superClassShortName, wrapperClassFullName, wrapperClassShortName, valueType, false);
        } else {
            //ObservablePrimitiveField
            String superClassShortName = superClassFullName.substring(superClassFullName.lastIndexOf('.') + 1);
            String wrapperClassShortName = superClassShortName + Const.WRAPPER_POST_FIX;
            String wrapperClassFullName = Const.GENERATED_PACKAGE + "." + wrapperClassShortName;
            String valueTypeName = ObservableClassesType.TYPE_CONNECTIONS.get(superClassShortName);
            return new WrapperData(superClassShortName, wrapperClassFullName, wrapperClassShortName, valueTypeName, true);
        }
    }

    /**
     * Generates wrapper class with overridden method set(value) which logs value which is set
     * @param wrapperData info about loggable field type
     * @param fieldName name of the observable field
     */
    private void createWrapper(WrapperData wrapperData, String fieldName) {
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("package", Const.GENERATED_PACKAGE);
        velocityContext.put("sourceClass", wrapperData.superClassShortName);
        velocityContext.put("generic", wrapperData.isPrimitive ? "" : "<T>");
        velocityContext.put("resultClassName", wrapperData.wrapperShortName);
        velocityContext.put("valueType", wrapperData.isPrimitive ? wrapperData.valueTypeName : "T");
        velocityContext.put("fieldName", fieldName);

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
