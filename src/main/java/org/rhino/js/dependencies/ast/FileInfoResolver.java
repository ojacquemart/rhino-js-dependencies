package org.rhino.js.dependencies.ast;

import org.mozilla.javascript.ast.AstRoot;
import org.rhino.js.dependencies.io.FileInfo;

import java.io.File;

public final class FileInfoResolver {

    private File file;

    private FileInfoResolver(File file) {
        this.file = file;
    }

    public static FileInfoResolver forFile(File file) {
        return new FileInfoResolver(file);
    }

    public FileInfo get() {
        try {
            AstRoot root = GetAstRoot.getRoot(file);

            JsFileVisitor visitor = new JsFileVisitor();
            visitor.startRootVisit(root);

            return visitor.toFileInfo();
        } catch (Exception e) {
            throw new IllegalStateException("Error during reading file " + file.getName(), e);
        }
    }

}

