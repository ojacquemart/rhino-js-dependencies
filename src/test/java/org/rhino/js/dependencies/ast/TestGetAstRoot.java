package org.rhino.js.dependencies.ast;

import org.junit.Test;
import org.mozilla.javascript.ast.AstRoot;

import static org.junit.Assert.*;

public class TestGetAstRoot {

    @Test(expected = IllegalStateException.class)
    public void testReadFromBadFileName() {
        GetAstRoot.getRoot("badfile.js");
    }

    @Test
    public void testReadFromFileName() {
        AstRoot root = GetAstRoot.getRoot(TestableJsFiles.SIMPLE.getFileName());
        assertNotNull(root);
    }
}
