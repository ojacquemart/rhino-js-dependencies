package org.rhino.js.dependencies.io;

public interface JsFileAttribute {

    /**
     * Returns the name of the file.
     */
    String name();

    /**
     * Returnss the number of lines of code.
     */
    int numberOfLoc();

    /**
     * Returns the the number of minified elements.
     */
    int numberOfMinified();

}
