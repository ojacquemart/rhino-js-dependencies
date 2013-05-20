package org.rhino.js.dependencies.ast;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.mozilla.javascript.ast.AstRoot;
import org.rhino.js.dependencies.io.Function;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;

public class TestJsFileVisitor {

    @Test
    public void testGetSimpleFunctions() {
        assertJsFile(TestableJsFiles.SIMPLE);
    }

    @Test
    public void testGetFunctionsFromPrototype() {
        assertJsFile(TestableJsFiles.PROTOTYPE);
    }

    @Test
    public void testGetFunctionsFromObject() {
        assertJsFile(TestableJsFiles.OBJECT);
    }

    @Test
    public void testGetFunctionsFromJqueryPlugin() {
        assertJsFile(TestableJsFiles.JQUERY_PLUGIN);
    }

    private static void assertJsFile(TestableJsFiles testFile) {
        AstRoot root = GetAstRoot.getRoot(testFile.getFileName());
        JsFileVisitor visitor = new JsFileVisitor();
        visitor.startRootVisit(root);

        int loc = visitor.getNumberLoc();
        assertTrue(loc > 0);

        Set<Function> functions = visitor.getFunctions();
        assertFalse(functions.isEmpty());
        assertEquals(testFile.getFunctionNames().length, functions.size());
        assertAllAreExcepted(functions, testFile.getFunctionNames());

        Set<Function> functionCalls = visitor.getFunctionCalls();
        assertEquals(testFile.getFunctionCallNames().length, functionCalls.size());
        assertAllAreExcepted(functionCalls, testFile.getFunctionCallNames());
    }

    // All functions found must be in excepted list.
    private static void assertAllAreExcepted(Collection<Function> actual, String[] excepted){
        assertThat(toStrings(actual), hasItems(excepted));
    }

    private static List<String> toStrings(Collection<Function> functions) {
        List<String> list = Lists.newArrayList();

        for (Function funcName : functions) {
            list.add(funcName.getName());
        }

        return list;
    }

}

