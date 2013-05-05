package org.rhino.js.dependencies.parser;

import org.mozilla.javascript.ast.AstRoot;

import java.io.File;
import java.util.Set;

public class FunctionResolver {

    private File file;

    private FunctionResolver(File file) {
        this.file = file;
    }

    public static FunctionResolver forFile(File file) {
        return new FunctionResolver(file);
    }

    public Set<String> getFunctions() {
        try {
            AstRoot root = GetAstRoot.getRoot(file);

            return FunctionNodeVisitor.visitAndGetFunctions(root);
        } catch (Exception e) {
            throw new IllegalStateException("Error during reading file " + file.getName(), e);
        }
    }

}

