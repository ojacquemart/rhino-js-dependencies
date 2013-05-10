package org.rhino.js.dependencies.parser;

import org.junit.Test;
import org.mozilla.javascript.ast.AstRoot;

import java.util.Set;

import static org.junit.Assert.*;

public class TestJsFileVisitor {

    private void assertJsFile(JsFiles jsFile) {
        AstRoot root = GetAstRoot.getRoot(jsFile.fileName());
        JsFileVisitor visitor = JsFileVisitor.newInstance(root);

        Set<?> functions = visitor.getFunctions();
        assertFalse(functions.isEmpty());
        assertEquals(jsFile.functionsNumber(), functions.size());

        Set<?> functionCalls = visitor.getFunctionCalls();
        assertEquals(jsFile.functionCallsNumber(), functionCalls.size());
    }

    @Test
    public void testGetSimpleFunctions() {
        assertJsFile(JsFiles.SIMPLE);
    }

    @Test
    public void testGetFunctionsFromPrototype() {
        assertJsFile(JsFiles.PROTOTYPE);
    }

    @Test
    public void testGetFunctionsFromObject() {
        assertJsFile(JsFiles.OBJECT);
    }

    @Test
    public void testGetFunctionsFromJqueryPlugin() {
        assertJsFile(JsFiles.JQUERY_PLUGIN);
    }
}

