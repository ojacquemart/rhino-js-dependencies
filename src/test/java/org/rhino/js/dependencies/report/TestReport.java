package org.rhino.js.dependencies.report;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.joda.time.DateTime;
import org.junit.Test;
import org.rhino.js.dependencies.models.FunctionName;
import org.rhino.js.dependencies.models.JsFile;
import org.rhino.js.dependencies.models.JsFileInfo;

import java.util.List;

import static org.junit.Assert.*;

public class TestReport {

    @Test(expected = IllegalArgumentException.class)
    public void testSetRootJsDirNull() {
        new Report().setRootJsDir(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetJsFilesNull() {
        new Report().setJsFiles(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetTemplateNull() {
        new Report().setTemplate(null);
    }

    @Test
    public void testGenerate() {
        JsFile jsFile = new JsFile("foo");
        JsFileInfo fileInfo = new JsFileInfo(
                Sets.newTreeSet(Lists.newArrayList(FunctionName.newInstance("1"))),
                Sets.newTreeSet(Lists.newArrayList(FunctionName.newInstance("2"))));
        jsFile.setFileInfo(fileInfo);
        jsFile.getUsages().add(new JsFile("aa"));
        jsFile.getUsages().add(new JsFile("b"));
        jsFile.getUsages().add(new JsFile("c"));
        List<JsFile> jsFiles = Lists.newArrayList(jsFile);

        Report report = new Report();
        report.setRootJsDir("d:/");
        report.setJsFiles(jsFiles);
        report.generate();
    }

    @Test
    public void testGetBaseDirInRootJsDir() {
        String baseDir = new Report().setRootJsDir("/jsdir").getBaseDir();
        assertEquals("/jsdir", baseDir);
    }

    @Test
    public void testGetBaseDirInOutputDir() {
        String baseDir = new Report().setOutputDir("/outputdir").setRootJsDir("/jsdir").getBaseDir();
        assertEquals("/outputdir", baseDir);
    }

    @Test
    public void testGetName() {
        // Report file name should be yyyymmdd_templatename.extension
        Report report = new Report();
        assertEquals(DateTime.now().toString(Report.DATE_PATTERN_YYMMDD_HHMM)
                        + "_"
                        + Template.TEXT.getReportName(), report.getName());
    }

}
