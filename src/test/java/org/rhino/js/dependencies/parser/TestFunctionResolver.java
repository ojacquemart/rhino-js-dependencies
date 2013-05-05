package org.rhino.js.dependencies.parser;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Test the function resolver.
 */
public class TestFunctionResolver {

    @Before
    public void setUp() {
    }

    private Set<String> getFunctions(String fileName) {
        File jsFile = new File(fileName);

        return FunctionResolver.forFile(jsFile).getFunctions();
    }

    @Test
    public void testGetFunctions() throws IOException {
        Set<String> functions = getFunctions(JsFiles.SIMPLE.fileName());
        assertNotNull(functions);
        assertFalse(functions.isEmpty());
    }

}
