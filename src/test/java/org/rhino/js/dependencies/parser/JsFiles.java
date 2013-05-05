package org.rhino.js.dependencies.parser;

/**
 * Enum of js files to test.
 * Use toString() method on element to get the fileName or #fileName()
 */
public enum JsFiles {

    SIMPLE("functions.js", 3),
    PROTOTYPE("prototype.js", 5)
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

    JsFiles(String fileName, int nbFunctions) {
        this.fileName = RESOURCES_DIR + fileName;
        this.nbFunctions = nbFunctions;
    }

    public String fileName(){
        return fileName;
    }

    public int functionsNumber() {
        return nbFunctions;
    }

    @Override
    public String toString() {
        return fileName;
    }


}
