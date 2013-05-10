package org.rhino.js.dependencies.io;

import org.rhino.js.dependencies.models.JsFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to browse a directory and get a list of File
 * or a list of JsFile.
 */
public class JsTreeFiles {

    private JsTreeFiles() {};

    /**
     * Gets a list of File from a directory.
     * The directory is browsed recursively.
     *
     * @param dir the directory to browse.
     * @return a list of File.
     */
    public static List<File> getFiles(String dir) {
        SimpleJsFileVisitor simpleJsFileVisitor = new SimpleJsFileVisitor();

        try {
            Files.walkFileTree(Paths.get(dir), simpleJsFileVisitor);
        } catch (IOException e) {
            throw new IllegalStateException("Error during walking tree " + dir, e);
        }

        return simpleJsFileVisitor.getFiles();
    }

    /**
     * Gets a list of JsFile since a directory.
     *
     * @param dir the directory to browse.
     * @return a list of JsFile
     * @see #getFiles(String)
     */
    public static List<JsFile> getJsFiles(String dir) {
        List<JsFile> jsFiles = new ArrayList();

        List<File> files = getFiles(dir);
        for (File eachFiles : files) {
            jsFiles.add(new JsFile(eachFiles));
        }

        return jsFiles;
    }

}
