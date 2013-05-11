package org.rhino.js.dependencies.models;

import static org.junit.Assert.*;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Set;

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

    private static JsFile getJsFile() {
        JsFile jsFile = getJsFile("foo");
        jsFile.setFileInfo(getJsFileInfo());

        return jsFile;
    }

    private JsFile getJsFileNulls() {
        JsFile jsFile = getJsFile("foo");
        jsFile.setFileInfo(new JsFileInfo(null, null));

        return jsFile;
    }

    private static JsFileInfo getJsFileInfo() {
        Set<FunctionName> functions = Sets.newTreeSet(Lists.newArrayList(FunctionName.newInstance("$#bind")));
        Set<FunctionName> functions1 = Sets.newTreeSet(Lists.newArrayList(FunctionName.newInstance("$#click")));

        return new JsFileInfo(functions, functions1);
    }

    @Test
    public void testUsages() {
        JsFile jsFile = getJsFile();
        assertFalse(jsFile.usesFunction(null));
        assertTrue(jsFile.usesFunction(FunctionName.newInstance("$#click")));
        assertFalse(jsFile.usesFunction(FunctionName.newInstance("$#bind")));
    }

    @Test
    public void testHas() {
        JsFile jsFile = getJsFile();
        assertFalse(jsFile.hasFunction(null));
        assertTrue(jsFile.hasFunction(FunctionName.newInstance("$#bind")));
        assertFalse(jsFile.hasFunction(FunctionName.newInstance("$#click")));
    }

    @Test
    public void testNullSafe() {
        JsFile jsFileNulls = getJsFileNulls();
        // Code should not throw NPE.
        FunctionName mix = FunctionName.newInstance("mix");
        jsFileNulls.hasFunction(mix);
        jsFileNulls.usesFunction(mix);
    }
}