package org.rhino.js.dependencies.parser;

import org.mozilla.javascript.ast.AstRoot;
import org.rhino.js.dependencies.models.JsFileInfo;

import java.io.File;

public class JsFileInfoResolver {

    private File file;

    private JsFileInfoResolver(File file) {
        this.file = file;
    }

    public static JsFileInfoResolver forFile(File file) {
        return new JsFileInfoResolver(file);
    }

    public JsFileInfo getJsFileInfo() {
        try {
            AstRoot root = GetAstRoot.getRoot(file);

            JsFileVisitor visitor = JsFileVisitor.newInstance(root);

            return new JsFileInfo(visitor.getFunctions(), visitor.getFunctionCalls());
        } catch (Exception e) {
            throw new IllegalStateException("Error during reading file " + file.getName(), e);
        }
    }

}

