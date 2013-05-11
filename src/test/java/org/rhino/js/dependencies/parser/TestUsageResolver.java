package org.rhino.js.dependencies.parser;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.rhino.js.dependencies.models.FunctionName;
import org.rhino.js.dependencies.models.JsFile;
import org.rhino.js.dependencies.models.JsFileInfo;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.*;

public class TestUsageResolver {

    @Test(expected = IllegalArgumentException.class)
    public void testResolveAllNulls() {
        UsageResolver.resolveUsagesBetween(null);
    }

    @Test
    public void resolve() {
        JsFile jsFile1 = getJsFile(
                listToSet(FunctionName.newInstance("a")),
                listToSet());
        assertTrue(jsFile1.getUsages().isEmpty());
        JsFile jsFile2 = getJsFile(
                listToSet(),
                listToSet(FunctionName.newInstance("a")));
        JsFile jsFile3 = getJsFile(
                listToSet(FunctionName.newInstance("$#click")),
                listToSet(FunctionName.newInstance("a")));
        ArrayList<JsFile> jsFiles = Lists.newArrayList(jsFile1, jsFile2, jsFile3);

        UsageResolver.resolveUsagesBetween(jsFiles);
        assertFalse(jsFile1.getUsages().isEmpty());
        assertEquals(2, jsFile1.getUsages().size());
    }

    private static Set<FunctionName> listToSet(FunctionName ... list) {
        return Sets.newTreeSet(Lists.newArrayList(list));
    }

    private static JsFile getJsFile(Set<FunctionName> functions, Set<FunctionName> functionCalls) {
        JsFile jsFile = new JsFile(UUID.randomUUID().toString());
        jsFile.setFileInfo(new JsFileInfo(functions, functionCalls));

        return jsFile;
    }


}
