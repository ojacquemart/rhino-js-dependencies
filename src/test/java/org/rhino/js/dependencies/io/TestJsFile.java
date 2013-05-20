package org.rhino.js.dependencies.io;

import static org.junit.Assert.*;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Set;

public class TestJsFile {

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
        assertTrue(getJsFile("a.js").hashCode() == getJsFile("a.js").hashCode());
        assertFalse(getJsFile("b.js").hashCode() == getJsFile("a.js").hashCode());
        assertTrue(getJsFile("a.js").equals(getJsFile("a.js")));
        assertFalse(getJsFile("b.js").equals(getJsFile("a.js")));
    }

    @Test
    public void testNumberOfMinified() {
        // Must be equal to 1 if its a minfied file.

        JsFile file = getJsFile("foo.js");
        assertEquals(0, file.numberOfMinified());

        JsFile minFile = getJsFile("foo.min.js");
        assertEquals(1, minFile.numberOfMinified());
    }

    private static FileInfo getJsFileInfo() {
        Set<Function> functions = Sets.newTreeSet(Lists.newArrayList(Function.newInstance("$#bind")));
        Set<Function> functions1 = Sets.newTreeSet(Lists.newArrayList(Function.newInstance("$#click")));

        return new FileInfo(functions, functions1);
    }

    @Test
    public void testUsages() {
        JsFile jsFile = getFooJsFile();
        assertFalse(jsFile.usesFunction(null));
        assertTrue(jsFile.usesFunction(Function.newInstance("$#click")));
        assertFalse(jsFile.usesFunction(Function.newInstance("$#bind")));
    }

    @Test
    public void testHas() {
        JsFile jsFile = getFooJsFile();
        assertFalse(jsFile.hasFunction(null));
        assertTrue(jsFile.hasFunction(Function.newInstance("$#bind")));
        assertFalse(jsFile.hasFunction(Function.newInstance("$#click")));
    }

    @Test
    public void testNullSafe() {
        JsFile jsFileNulls = getJsFileNulls();
        // Code should not throw NPE.
        Function mix = Function.newInstance("mix");
        jsFileNulls.hasFunction(mix);
        jsFileNulls.usesFunction(mix);
    }

    private static JsFile getJsFile(String fileName) {
        JsFile jsFile = new JsFile(fileName);
        jsFile.setFileInfo(getJsFileInfo());

        return jsFile;
    }
    private static JsFile getFooJsFile() {
        return getJsFile("foo.js");
    }

    private static JsFile getJsFileNulls() {
        JsFile jsFile = getFooJsFile();
        jsFile.setFileInfo(new FileInfo(null, null));

        return jsFile;
    }
}