package org.rhino.js.dependencies.io;

import java.util.Set;

/**
 * A set of information to search from a javascript file.
 */
public interface FileInfoAttribute {

    /**
     * Gets the functions declared in a javascript file.
     *
     * @return a list of functions.
     */
    Set<Function> getFunctions();

    /**
     * Gets the functions called in a javascript file.
     *
     * @return a list of called functions.
     */
    Set<Function> getFunctionCalls();

    /**
     * Gets the number of lines of code.
     *
     * @return the number of loc.
     */
    int getNumberLoc();

}
