package com.cloudmade.xlogger;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;

class WrapperDataProcessor {

    /**
     * Creates info about annotated elements
     * @param elements annotated elements
     * @return list with info about annotated elements (field name and wrapper)
     */
    List<AnnotatedElementEntity> process(List<Element> elements) {
        List<AnnotatedElementEntity> annotatedElementEntities = new ArrayList<>();
        for (Element element : elements) {
            annotatedElementEntities.add(new AnnotatedElementEntity(element.getSimpleName().toString(),
                    createWrapperData(element.asType().toString())));
        }
        return annotatedElementEntities;
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
}
