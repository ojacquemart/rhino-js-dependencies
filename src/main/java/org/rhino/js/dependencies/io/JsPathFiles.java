package org.rhino.js.dependencies.io;

import org.rhino.js.dependencies.models.JsFile;
import org.rhino.js.dependencies.models.JsPath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Class to browse a directory and get a list of File
 * or a list of JsFile.
 */
public final class JsPathFiles {

    private static final JsPathVisitor VISITOR = new JsPathVisitor();

    private JsPathFiles() {
    }

    /**
     * Gets a list of JsFile from a directory.
     * The directory is browsed recursively.
     *
     * @param dir the directory to browse.
     * @return a list of JsFile.
     */
    public static List<JsFile> getFiles(String dir) {
        walkTree(dir);

        return VISITOR.getFiles();

    }

    /**
     * Gets a list of JsPath with its javascript files from a root dir.
     *
     * @param dir the root dir.
     * @return a list of JsPath.
     */
    public static List<JsPath> getPaths(String dir) {
        walkTree(dir);

        return VISITOR.getPaths();
    }

    private static void walkTree(String dir) {
        try {
            Files.walkFileTree(Paths.get(dir), VISITOR);
        } catch (IOException e) {
            throw new IllegalStateException("Error during walking tree " + dir, e);
        }
    }

}
