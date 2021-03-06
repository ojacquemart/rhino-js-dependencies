package org.rhino.js.dependencies.ast;

import org.junit.Before;
import org.junit.Test;
import org.rhino.js.dependencies.io.FileInfo;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Test the function resolver.
 */
public class JsFileInfoResolver {

    @Before
    public void setUp() {
    }

    private FileInfo getJsFileInfo(String fileName) {
        File jsFile = new File(fileName);

        return FileInfoResolver.forFile(jsFile).get();
    }

    @Test
    public void testGetFunctions() throws IOException {
        FileInfo fileInfo = getJsFileInfo(TestableJsFiles.SIMPLE.getFileName());

        Set<?> functions = fileInfo.getFunctions();
        assertNotNull(functions);
        assertFalse(functions.isEmpty());
    }

}
