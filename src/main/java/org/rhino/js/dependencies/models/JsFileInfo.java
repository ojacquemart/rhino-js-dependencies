package org.rhino.js.dependencies.models;

import java.util.Collections;
import java.util.Set;

/**
 * JsFile information with list of functions and function calls.
 */
public class JsFileInfo {

    private final Set<FunctionName> functions;
    private final Set<FunctionName> functionCalls;

    public JsFileInfo(Set<FunctionName> functions, Set<FunctionName> functionCalls) {
        this.functions = functions;
        this.functionCalls = functionCalls;
    }

    public static JsFileInfo emptyFileInfo() {
        return new JsFileInfo(Collections.<FunctionName>emptySet(), Collections.<FunctionName>emptySet());
    }

    public Set<FunctionName> getFunctions() {
        return functions;
    }

    public Set<FunctionName> getFunctionCalls() {
        return functionCalls;
    }
}
