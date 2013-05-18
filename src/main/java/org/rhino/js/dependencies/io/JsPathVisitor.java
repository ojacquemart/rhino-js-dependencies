package org.rhino.js.dependencies.io;

import com.google.common.io.Files;
import org.rhino.js.dependencies.models.JsFile;
import org.rhino.js.dependencies.models.JsPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsPathVisitor implements FileVisitor<Path> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsPathVisitor.class);

    /**
     * Map with js files by path.
     * The map is sorted by its key path cause the dirs are visited in asc order.
     */
    private Map<String, List<JsFile>> filesByPath = new HashMap<>();

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        LOGGER.debug("Visit dir {}", dir.toAbsolutePath());

        // Initialize the key path with an empty list.
        String fullPath = dir.toString();
        List<JsFile> files = filesByPath.get(fullPath);
        if (files == null) {
            filesByPath.put(fullPath, new ArrayList<JsFile>());
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (hasJsExtension(file.toString())) {
            LOGGER.debug("Found js file {}", file.getFileName());

            Path parent = file.getParent();
            List<JsFile> files = filesByPath.get(parent.toString());
            files.add(new JsFile(file.toFile()));
        }

        return FileVisitResult.CONTINUE;
    }

    private static boolean hasJsExtension(String fullName) {
        return "js".equals(Files.getFileExtension(fullName));
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {

        return FileVisitResult.CONTINUE;
    }

    /**
     * Gets all the files flatten.
     * @return a list of JsFile.
     */
    public List<JsFile> getFiles() {
        List<JsFile> files = new ArrayList<>();

        for (Map.Entry<String, List<JsFile>> entry : filesByPath.entrySet()) {
            List<JsFile> pathFiles = entry.getValue();
            LOGGER.debug("Path {} with {}", entry.getKey(), pathFiles);

            files.addAll(pathFiles);
        }

        return files;
    }

    /**
     * Gets all the dir with javascript files.
     * @return a list of JsPath.
     */
    public List<JsPath> getPaths() {
        List<JsPath> paths = new ArrayList<>();

        for (Map.Entry<String, List<JsFile>> entry : filesByPath.entrySet()) {
            String dir = entry.getKey();
            List<JsFile> pathFiles = entry.getValue();
            LOGGER.debug("Path {} with {}", dir, pathFiles);

            // Keep only path with javascript files in it.
            if (!pathFiles.isEmpty()) {
                paths.add(new JsPath(dir, pathFiles));
            }
        }

        return paths;
    }

}
