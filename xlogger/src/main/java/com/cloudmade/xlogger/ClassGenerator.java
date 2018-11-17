package com.cloudmade.xlogger;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;

abstract class ClassGenerator {

    ProcessingEnvironment processingEnvironment;
    VelocityEngine velocityEngine;

    ClassGenerator(ProcessingEnvironment processingEnvironment, VelocityEngine velocityEngine) {
        this.processingEnvironment = processingEnvironment;
        this.velocityEngine = velocityEngine;
    }

    boolean writeFile(String fileName, VelocityTemplate velocityTemplate, VelocityContext velocityContext) {
        try {
            JavaFileObject source = processingEnvironment.getFiler().createSourceFile(fileName);
            Writer writer = source.openWriter();
            velocityEngine.getTemplate(velocityTemplate.templatePath).merge(velocityContext, writer);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    String mergeVelocityContext(VelocityContext velocityContext, VelocityTemplate velocityTemplate) {
        Template template = velocityEngine.getTemplate(velocityTemplate.templatePath);
        StringWriter sw = new StringWriter();
        template.merge(velocityContext, sw);
        return sw.toString();
    }
}
