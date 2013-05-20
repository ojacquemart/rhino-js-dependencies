package org.rhino.js.dependencies.ast;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.rhino.js.dependencies.io.FileInfo;
import org.rhino.js.dependencies.io.Function;
import org.rhino.js.dependencies.io.JsFile;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.*;

public class TestFileUsageResolver {

    private FilesUsageResolver filesUsageResolver = new FilesUsageResolver();

    @Test(expected = IllegalArgumentException.class)
    public void testResolveAllNulls() {
        filesUsageResolver.resolve(null);
    }

    @Test
    public void resolve() {
        JsFile jsFile1 = getJsFile(
                listToSet(Function.newInstance("a")),
                listToSet());
        assertTrue(jsFile1.getUsages().isEmpty());
        JsFile jsFile2 = getJsFile(
                listToSet(),
                listToSet(Function.newInstance("a")));
        JsFile jsFile3 = getJsFile(
                listToSet(Function.newInstance("$#click")),
                listToSet(Function.newInstance("a")));
        ArrayList<JsFile> jsFiles = Lists.newArrayList(jsFile1, jsFile2, jsFile3);

        filesUsageResolver.resolve(jsFiles);
        assertFalse(jsFile1.getUsages().isEmpty());
        assertEquals(2, jsFile1.getUsages().size());
    }

    private static Set<Function> listToSet(Function... list) {
        return Sets.newTreeSet(Lists.newArrayList(list));
    }

    private static JsFile getJsFile(Set<Function> functions, Set<Function> functionCalls) {
        JsFile jsFile = new JsFile(UUID.randomUUID().toString());
        jsFile.setFileInfo(new FileInfo(functions, functionCalls));

        return jsFile;
    }


}
