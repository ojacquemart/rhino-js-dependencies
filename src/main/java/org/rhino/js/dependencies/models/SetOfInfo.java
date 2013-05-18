package org.rhino.js.dependencies.models;

import java.util.Set;

/**
 * A set of information to search from a javascript file.
 */
public interface SetOfInfo {

    /**
     * Gets the functions declared in a javascript file.
     *
     * @return a list of functions.
     */
    Set<FunctionName> getFunctions();

    /**
     * Gets the functions called in a javascript file.
     *
     * @return a list of called functions.
     */
    Set<FunctionName> getFunctionCalls();

    /**
     * Gets the number of lines of code.
     *
     * @return the number of loc.
     */
    int getLinesOfCode();
}
