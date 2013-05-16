package org.rhino.js.dependencies.parser;

import org.junit.Before;
import org.junit.Test;
import org.rhino.js.dependencies.models.JsFileInfo;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Test the function resolver.
 */
public class TestJsFileInfoResolver {

    @Before
    public void setUp() {
    }

    private JsFileInfo getJsFileInfo(String fileName) {
        File jsFile = new File(fileName);

        return JsFileInfoResolver.forFile(jsFile).getJsFileInfo();
    }

    @Test
    public void testGetFunctions() throws IOException {
        JsFileInfo jsFileInfo = getJsFileInfo(TestableJsFiles.SIMPLE.getFileName());

        Set<?> functions = jsFileInfo.getFunctions();
        assertNotNull(functions);
        assertFalse(functions.isEmpty());
    }

}
