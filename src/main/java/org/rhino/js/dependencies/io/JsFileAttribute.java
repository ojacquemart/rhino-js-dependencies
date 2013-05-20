package org.rhino.js.dependencies.io;

public interface JsFileAttribute {

    /**
     * Returns the name of the file.
     */
    String getName();

    /**
     * Returnss the number of lines of code.
     */
    int getNumberOfLoc();

    /**
     * Returns the the number of minified elements.
     */
    int getNumberOfMinified();

}
