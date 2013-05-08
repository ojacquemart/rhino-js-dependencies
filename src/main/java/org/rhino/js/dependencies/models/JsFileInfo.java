package org.rhino.js.dependencies.models;

import java.util.Set;
import java.util.TreeSet;

/**
 * JsFile information with list of functions and function calls.
 */
public class JsFileInfo {

    private final Set<String> functions;
    private final Set<String> functionCalls;

    public JsFileInfo(Set<String> functions, Set<String> functionCalls) {
        this.functions = functions;
        this.functionCalls = functionCalls;
    }

    public Set<String> getFunctions() {
        return functions;
    }

    public Set<String> getFunctionCalls() {
        return functionCalls;
    }
}
