package org.rhino.js.dependencies.parser;

import com.google.common.collect.Lists;
import org.rhino.js.dependencies.models.JsFile;

import java.util.Collections;
import java.util.List;

/**
 * Enum of js files to test.
 * Use toString() method on element to get the getFileName or #getFileName()
 */
public enum TestableJsFiles {

    SIMPLE("functions.js",
            Lists.newArrayList("a", "b", "c", "d", "e")),
    PROTOTYPE("prototype.js",
            Lists.newArrayList("Proto", "Proto#static1", "Proto#static2", "Proto#classic1", "Proto#classic2"),
            Lists.newArrayList("Proto#static1", "Proto#classic1")),
    OBJECT("object.js",
            Lists.newArrayList("purl", "purl#segment", "purl#fsegment"),
            Lists.newArrayList("purl", "purl#segment", "$#animate")),
    JQUERY_PLUGIN("jquery.plugin.js",
            Lists.newArrayList("$#autoSuggest", "$#safeSearch"))
    ;

    private static final String RESOURCES_DIR = "src/test/resources/";

    /**
     * The file name.
     */
    private String fileName;

    /**
     * The functions in the file.
     */
    private List<String> functionNames;

    /**
     * The function calls in the file.
     */
    private List<String> functionCallNames;

    private TestableJsFiles(String fileName, List<String> functionNames, List<String> functionCallNames) {
        this.fileName = RESOURCES_DIR + fileName;
        this.functionNames = functionNames;
        this.functionCallNames = functionCallNames;
    }

    private TestableJsFiles(String fileName, List<String> functionNames) {
        this(fileName, functionNames, Collections.<String>emptyList());
    }

    public String getFileName(){
        return fileName;
    }

    public JsFile toJsFile() {
        return new JsFile(getFileName());
    }

    public String[] getFunctionNames() {
        return toArray(functionNames);
    }

    public String[] getFunctionCallNames() {
        return toArray(functionCallNames);
    }

    private static String[] toArray(List<String> list) {
        if (list == null) {
            return new String[] {};
        }

        return list.toArray(new String[list.size()]);
    }

    @Override
    public String toString() {
        return fileName;
    }

}
