package org.rhino.js.dependencies.models;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestJsFile {

    private static JsFile getJsFile(String path) {
        return new JsFile(path);
    }

    @Test
    public void testComparable() {
        int sameCompareTo = getJsFile("/foo.js").compareTo(getJsFile("/foo.js"));
        assertEquals(0, sameCompareTo);

        int lowerCompareTo =getJsFile("/libs/a.js").compareTo(getJsFile("/libs/b.js"));
        assertEquals(-1, lowerCompareTo);

        int greaterCompareTo = getJsFile("/libs/b.js").compareTo(getJsFile("/libs/a.js"));
        assertEquals(1, greaterCompareTo);
    }

    @Test
    public void testHashcodeAndEquals() {
        assertTrue(getJsFile("a").hashCode() == getJsFile("a").hashCode());
        assertFalse(getJsFile("b").hashCode() == getJsFile("a").hashCode());
        assertTrue(getJsFile("a").equals(getJsFile("a")));
        assertFalse(getJsFile("b").equals(getJsFile("a")));
    }
}