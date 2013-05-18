package org.rhino.js.dependencies.parser;

import org.mozilla.javascript.ast.AstRoot;
import org.rhino.js.dependencies.models.FileInfo;

import java.io.File;

public class FileInfoResolver {

    private File file;

    private FileInfoResolver(File file) {
        this.file = file;
    }

    public static FileInfoResolver forFile(File file) {
        return new FileInfoResolver(file);
    }

    public FileInfo getJsFileInfo() {
        try {
            AstRoot root = GetAstRoot.getRoot(file);

            JsFileVisitor visitor = new JsFileVisitor();
            visitor.startRootVisit(root);

            return new FileInfo(visitor.getFunctions(), visitor.getFunctionCalls());
        } catch (Exception e) {
            throw new IllegalStateException("Error during reading file " + file.getName(), e);
        }
    }

}

