package org.rhino.js.dependencies.parser;

import org.junit.Test;
import org.mozilla.javascript.ast.AstRoot;

import java.util.Set;

import static org.junit.Assert.*;

public class TestFunctionNodeVisitor {

    @Test
    public void testGetSimpleFunctions() {
        AstRoot root = GetAstRoot.getRoot(JsFiles.SIMPLE.fileName());
        Set<String> functions = FunctionNodeVisitor.visitAndGetFunctions(root);

        assertFalse(functions.isEmpty());
        assertEquals(JsFiles.SIMPLE.functionsNumber(), functions.size());
    }
}

