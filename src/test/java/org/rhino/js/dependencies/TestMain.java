package org.rhino.js.dependencies;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.*;

public class TestMain {

    private Main main;

    private static final String CURRENT_PATH = Paths.get("").toAbsolutePath().toString();

    @Before
    public void setUp() {
        main = new Main();
    }

    @Test
    public void testNoProperties() {
        assertFalse(main.setAndCheckPropDirs());
    }

    @Test
    public void testInvalidJsDir() {
        System.setProperty("jsdir", "INVALID");
        assertFalse(main.setAndCheckPropDirs());
    }

    @Test
    public void testValidJsDir() {
        System.setProperty("jsdir", CURRENT_PATH);
        assertTrue(main.setAndCheckPropDirs());
    }

    @Test
    public void testValidJsDirAndInvalidOutputdir() {
        System.setProperty("jsdir", CURRENT_PATH);
        System.setProperty("outputdir", "INVALID");
        assertFalse(main.setAndCheckPropDirs());
    }

    @Test
    public void testValidJsDirAndOutputdir() {
        System.setProperty("jsdir", CURRENT_PATH);
        System.setProperty("outputdir", CURRENT_PATH);
        assertTrue(main.setAndCheckPropDirs());
    }

}
