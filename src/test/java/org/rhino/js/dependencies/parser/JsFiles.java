package org.rhino.js.dependencies.parser;

import org.rhino.js.dependencies.models.JsFile;

/**
 * Enum of js files to test.
 * Use toString() method on element to get the fileName or #fileName()
 */
public enum JsFiles {

    SIMPLE("functions.js", 3, 0),
    PROTOTYPE("prototype.js", 6, 2),
    OBJECT("object.js", 6, 2),
    JQUERY_PLUGIN("jquery.plugin.js", 2, 0),
    ;

    private static final String RESOURCES_DIR = "src/test/resources/";

    /**
     * The file name.
     */
    private String fileName;

    /**
     * The number of functions in the file.
     */
    private int nbFunctions;

    /**
     * The number of function calls in the file.
     */
    private int nbFunctionCalls;

    JsFiles(String fileName, int nbFunctions, int nbFunctionCalls) {
        this.fileName = RESOURCES_DIR + fileName;
        this.nbFunctions = nbFunctions;
        this.nbFunctionCalls = nbFunctionCalls;
    }

    public String fileName(){
        return fileName;
    }

    public JsFile getJsFile() {
        return new JsFile(fileName());
    }

    public int functionsNumber() {
        return nbFunctions;
    }

    public int functionCallsNumber() {
        return nbFunctionCalls;
    }

    @Override
    public String toString() {
        return fileName;
    }


}
