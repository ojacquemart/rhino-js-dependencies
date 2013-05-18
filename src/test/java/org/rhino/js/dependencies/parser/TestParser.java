package org.rhino.js.dependencies.parser;

import org.junit.Test;
import org.rhino.js.dependencies.io.JsPathFiles;
import org.rhino.js.dependencies.models.JsFile;
import org.rhino.js.dependencies.models.JsFileInfo;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class TestParser {

    @Test
    public void testParse() {
        JsFile jsFile = TestableJsFiles.JQUERY_PLUGIN.toJsFile();
        Parser.parse(jsFile);
        assertJsFileInfo(jsFile.getFileInfo());
    }

    @Test
    public void testParseAll() throws IOException {
        List<JsFile> jsFiles = JsPathFiles.getFiles("src/test");
        Parser.parseAll(jsFiles);
        for (JsFile eachJsFile : jsFiles) {
            JsFileInfo fileInfo = eachJsFile.getFileInfo();
            assertJsFileInfo(fileInfo);
        }
    }

    private static void assertJsFileInfo(JsFileInfo fileInfo) {
        assertNotNull(fileInfo);
        assertNotNull(fileInfo.getFunctions());
        assertTrue(fileInfo.getFunctions().size() > 0);
    }

}
