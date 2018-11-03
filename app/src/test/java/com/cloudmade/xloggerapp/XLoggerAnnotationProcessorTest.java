package com.cloudmade.xloggerapp;

import com.cloudmade.xloggerapp.sampledata.SampleDataFile;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import static junit.framework.Assert.assertTrue;

public class XLoggerAnnotationProcessorTest {

    @Test
    public void generatedLogInitializer_isCorrect() {
        testGeneratedFile(SampleDataFile.LOG_INITIALIZER);
    }

    @Test
    public void generatedObservableBooleanWrapper_isCorrect() {
        testGeneratedFile(SampleDataFile.BOOLEAN_WRAPPER);
    }

    @Test
    public void generatedObservableByteWrapper_isCorrect() {
        testGeneratedFile(SampleDataFile.BYTE_WRAPPER);
    }

    @Test
    public void generatedObservableCharWrapper_isCorrect() {
        testGeneratedFile(SampleDataFile.CHAR_WRAPPER);
    }

    @Test
    public void generatedObservableDoubleWrapper_isCorrect() {
        testGeneratedFile(SampleDataFile.DOUBLE_WRAPPER);
    }

    @Test
    public void generatedObservableFloatWrapper_isCorrect() {
        testGeneratedFile(SampleDataFile.FLOAT_WRAPPER);
    }

    @Test
    public void generatedObservableIntWrapper_isCorrect() {
        testGeneratedFile(SampleDataFile.INT_WRAPPER);
    }

    @Test
    public void generatedObservableLongWrapper_isCorrect() {
        testGeneratedFile(SampleDataFile.LONG_WRAPPER);
    }

    @Test
    public void generatedObservableShortWrapper_isCorrect() {
        testGeneratedFile(SampleDataFile.SHORT_WRAPPER);
    }

    @Test
    public void generatedObservableFieldWrapper_isCorrect() {
        testGeneratedFile(SampleDataFile.FIELD_WRAPPER);
    }

    private void testGeneratedFile(SampleDataFile sampleDataFile) {
        File actualGeneratedJavaFile = new File("build/generated/source/apt/debug/" + sampleDataFile.generatedClass.getPackage().getName().replace('.', '/') + "/" + sampleDataFile.generatedClass.getSimpleName() + ".java");
        File expectedGeneratedFileContent = new File("src/main/resources/samplegenerateddata/" + sampleDataFile.expectedContentPath);

        assertTrue("Generated class content is not valid",
                isContentTheSame(actualGeneratedJavaFile, expectedGeneratedFileContent));
    }

    private boolean isContentTheSame(File f1, File f2) {
        try {
            byte[] actualBytesArray = Files.readAllBytes(f1.toPath());
            byte[] expectedBytesArray = Files.readAllBytes(f2.toPath());
            return Arrays.equals(actualBytesArray, expectedBytesArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
