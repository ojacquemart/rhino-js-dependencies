package org.rhino.js.dependencies.parser;

import org.junit.Test;
import org.mozilla.javascript.ast.AstRoot;

import static org.junit.Assert.*;

public class TestAstGet {

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
