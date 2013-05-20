package org.rhino.js.dependencies.ast;

import org.junit.Test;
import org.rhino.js.dependencies.io.FileInfo;

import java.io.File;

import static org.junit.Assert.*;

public class TestFileInfoResolver {

    @Test
    public void testGet() {
        File file = TestableJsFiles.JQUERY_PLUGIN.toFile();
        FileInfo fileInfo = FileInfoResolver.forFile(file).get();
        assertJsFileInfo(file.getName(), fileInfo);
    }


    @Test(expected = IllegalStateException.class)
    public void testGetIllegalState() {
        File fileNotFound = new File("foo.js");
        FileInfo fileInfo = FileInfoResolver.forFile(fileNotFound).get();
        assertJsFileInfo(fileNotFound.getName(), fileInfo);
    }

    private static void assertJsFileInfo(String fileName, FileInfo fileInfo) {
        assertNotNull("fileInfo is null for " + fileName, fileInfo);
        assertNotNull("Functions is null for " + fileName, fileInfo.getFunctions());
        assertTrue("File functions is empty for " + fileName, fileInfo.getFunctions().size() > 0);
    }

}
