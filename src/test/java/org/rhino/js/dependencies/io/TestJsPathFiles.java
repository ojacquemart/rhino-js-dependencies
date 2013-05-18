package org.rhino.js.dependencies.io;

import org.junit.Test;
import org.rhino.js.dependencies.models.JsFile;
import org.rhino.js.dependencies.models.JsPath;

import java.util.List;

import static org.junit.Assert.*;

public class TestJsPathFiles {

    @Test
    public void testGetFiles() {
        String dir = "src/test";

        List<JsFile> jsFiles = JsPathFiles.getFiles(dir);
        for (JsFile eachJsFile : jsFiles) {
            assertNotNull(eachJsFile.getFile());
        }
    }

    @Test
    public void testGetPaths() {
        String dir = "src/test";

        List<JsPath> paths = JsPathFiles.getPaths(dir);
        assertNotNull(paths);
        assertEquals(2, paths.size());
        for (JsPath path : paths) {
            assertNotNull(path.getFiles());
            assertFalse(path.getFiles().isEmpty());
        }

        // First path must be first the resources root dir.
        assertTrue(paths.get(0).getDir().endsWith("resources"));
    }
}
