package org.rhino.js.dependencies.parser;

import org.junit.Test;
import org.rhino.js.dependencies.io.JsPathFiles;
import org.rhino.js.dependencies.models.FileInfo;
import org.rhino.js.dependencies.models.JsFile;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class TestParser {

    @Test
    public void testParse() {
        JsFile jsFile = TestableJsFiles.JQUERY_PLUGIN.toJsFile();
        Parser.parse(jsFile);
        assertJsFileInfo(jsFile.getFile().getName(), jsFile.getFileInfo());
    }

    @Test
    public void testParseAll() throws IOException {
        List<JsFile> jsFiles = JsPathFiles.getFiles("src/test");
        Parser.parseAll(jsFiles);
        for (JsFile eachJsFile : jsFiles) {
            FileInfo fileInfo = eachJsFile.getFileInfo();
            assertJsFileInfo(eachJsFile.getFile().getName(), fileInfo);
        }
    }

    private static void assertJsFileInfo(String fileName, FileInfo fileInfo) {
        assertNotNull("fileInfo is null for " + fileName, fileInfo);
        assertNotNull("Functions is null for " + fileName, fileInfo.getFunctions());
        assertTrue("File functions is empty for " + fileName, fileInfo.getFunctions().size() > 0);
    }

}
