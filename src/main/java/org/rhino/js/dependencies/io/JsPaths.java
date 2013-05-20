package org.rhino.js.dependencies.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

/**
 * Set of static helper methods around the JsPaths class.
 */
public final class JsPaths {

    private static final FileJsVisitor VISITOR = new FileJsVisitor();

    private JsPaths() {
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

    /**
     * Returns the total of lines of code for a Collection of JsFileAttribute.
     *
     * @param fileAttributes the list of JsFileAttribute.
     * @return the total number of lines of code.
     */
    public static int getTotalOfLoc(Collection<? extends JsFileAttribute> fileAttributes) {
        int total = 0;
        for (JsFileAttribute fileAttribute : fileAttributes) {
            total += fileAttribute.numberOfLoc();
        }

        return total;
    }

    /**
     * Returns the total of files in a Collection of JsPath.
     *
     * @param paths the list of JsPath.
     * @return the total number of files.
     */
    public static int getNumberOfFiles(Collection<JsPath> paths) {
        int total = 0;
        for (JsPath path : paths) {
            total += path.getFiles().size();
        }

        return total;
    }

    /**
     * Returns the total of minified files in a Collection of JsPath.
     *
     * @param fileAttributes the list of JsPath.
     * @return the number of minified files.
     */
    public static int getNumberOfMinifiedFiles(Collection<? extends JsFileAttribute> fileAttributes) {
        int total = 0;
        for (JsFileAttribute fileAttribute : fileAttributes) {
            total += fileAttribute.numberOfMinified();
        }

        return total;
    }
}
