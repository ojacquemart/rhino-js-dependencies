package org.rhino.js.dependencies.io;

import java.util.Collections;
import java.util.Set;

/**
 * JsFile information with a set of attributes.
 */
public class FileInfo implements FileInfoAttribute {

    private final Set<Function> functions;
    private final Set<Function> functionCalls;
    private int loc;

    public FileInfo(Set<Function> functions, Set<Function> functionCalls) {
        this(functions, functionCalls, 0);
    }

    public FileInfo(Set<Function> functions, Set<Function> functionCalls, int loc) {
        this.functions = functions;
        this.functionCalls = functionCalls;
        this.loc = loc;
    }

    public static FileInfo emptyFileInfo() {
        return new FileInfo(Collections.<Function>emptySet(), Collections.<Function>emptySet());
    }

    @Override
    public Set<Function> getFunctions() {
        return functions;
    }

    @Override
    public Set<Function> getFunctionCalls() {
        return functionCalls;
    }

    @Override
    public int getNumberLoc() {
        return loc;
    }
}
