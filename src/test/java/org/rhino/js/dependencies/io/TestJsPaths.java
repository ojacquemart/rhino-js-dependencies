package org.rhino.js.dependencies.io;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class TestJsPaths {

    @Test
    public void testGetFiles() {
        String dir = "src/test";

        List<JsFile> jsFiles = JsPaths.getFiles(dir);
        for (JsFile eachJsFile : jsFiles) {
            assertNotNull(eachJsFile.getFile());
        }
    }

    @Test
    public void testGetPaths() {
        String dir = "src/test";

        List<JsPath> paths = JsPaths.getPaths(dir);
        assertNotNull(paths);
        assertEquals(2, paths.size());
        for (JsPath path : paths) {
            assertNotNull(path.getFiles());
            assertFalse(path.getFiles().isEmpty());
        }

        // First path must be first the resources root dir.
        assertTrue(paths.get(0).getName().endsWith("resources"));
    }

    @Test
    public void testGetTotalOfLoc() throws Exception {
        // Each file has a loc equals to its index +1 in the list.
        assertEquals(0, JsPaths.getTotalOfLoc(getFiles(0)));
        assertEquals(1, JsPaths.getTotalOfLoc(getFiles(1)));
        // 1...10 sum == 55
        assertEquals(55, JsPaths.getTotalOfLoc(getFiles(10)));
    }

    @Test
    public void testGetNumberOfFiles() throws Exception {
        assertEquals(0, JsPaths.getNumberOfFiles(getPaths(0)));
        assertEquals(10, JsPaths.getNumberOfFiles(getPaths(1)));
        assertEquals(100, JsPaths.getNumberOfFiles(getPaths(10)));
    }

    @Test
    public void testGetNumberOfMinifiedFiles() throws Exception {
        List<JsFile> files = Lists.newArrayList(
                getFile(null),
                getFile("angular.min.js"),
                getFile("jquery.min.js"),
                getFile(null)
        );
        assertEquals(2, JsPaths.getNumberOfMinifiedFiles(files));
    }

    private static List<JsPath> getPaths(int number) {
        List<JsPath> paths = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            JsPath path = new JsPath(randomUUID());
            path.getFiles().addAll(getFiles(10));

            paths.add(path);

        }

        return paths;
    }

    private static List<JsFile> getFiles(int number) {
        List<JsFile> files = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            files.add(getFile(i + 1));
        }

        return files;
    }

    private static JsFile getFile(String fileName) {
        return getFile(fileName, 0);
    }

    private static JsFile getFile(int loc) {
        return getFile(null, loc);
    }

    private static JsFile getFile(String fileName, int loc) {

        JsFile jsFile = new JsFile(generateOrGetFileName(fileName));
        jsFile.setFileInfo(getFileInfo(loc));

        return jsFile;
    }

    private static String generateOrGetFileName(String fileName) {
        if (fileName == null) {
            return randomUUID();
        }

        return fileName;
    }

    private static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    private static FileInfo getFileInfo(int loc) {
        Set<Function> emptyFunctions = Collections.<Function>emptySet();

        return new FileInfo(emptyFunctions, emptyFunctions, loc);
    }
}
