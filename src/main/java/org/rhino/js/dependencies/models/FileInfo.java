package org.rhino.js.dependencies.models;

import java.util.Collections;
import java.util.Set;

/**
 * JsFile information with a set of informations.
 */
public class FileInfo implements SetOfInfo {

    private final Set<FunctionName> functions;
    private final Set<FunctionName> functionCalls;
    private int loc;

    public FileInfo(Set<FunctionName> functions, Set<FunctionName> functionCalls) {
        this(functions, functionCalls, 0);
    }

    public FileInfo(Set<FunctionName> functions, Set<FunctionName> functionCalls, int loc) {
        this.functions = functions;
        this.functionCalls = functionCalls;
        this.loc = loc;
    }

    public static FileInfo emptyFileInfo() {
        return new FileInfo(Collections.<FunctionName>emptySet(), Collections.<FunctionName>emptySet());
    }

    @Override
    public Set<FunctionName> getFunctions() {
        return functions;
    }

    @Override
    public Set<FunctionName> getFunctionCalls() {
        return functionCalls;
    }

    @Override
    public int getLinesOfCode() {
        return loc;
    }
}
