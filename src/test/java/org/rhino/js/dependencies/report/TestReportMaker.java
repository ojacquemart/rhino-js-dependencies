package org.rhino.js.dependencies.report;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestReportMaker {

    @Test(expected = IllegalArgumentException.class)
    public void testSetTemplateNull() {
        new ReportMaker().setTemplate(null);
    }

    @Test
    public void testGenerate() {
        ReportMaker reportMaker = new ReportMaker();
        reportMaker.prepareData("foo", "src/test")
                .setTemplate("html")
                .enableTestMode()
                .generate();
    }

    @Test
    public void testGenerateAllTemplates() {
        for (Template template : Template.values()) {
            ReportMaker reportMaker = new ReportMaker();
            reportMaker.prepareData("foo", "src/test")
                    .setTemplate(template.getType())
                    .enableTestMode()
                    .generate();
        }
    }

    @Test
    public void testGetBaseDirInRootJsDir() {
        String baseDir = new ReportMaker().prepareData("foo", "/jsdir").getBaseDir();
        assertEquals("/jsdir", baseDir);
    }

    @Test
    public void testGetBaseDirInOutputDir() {
        String baseDir = new ReportMaker().setOutputDir("/outputdir").prepareData("foo", "/jsdir").getBaseDir();
        assertEquals("/outputdir", baseDir);
    }

    @Test
    public void testGetName() {
        // Report file name should be yyyymmdd_templatename.extension
        ReportMaker report = new ReportMaker();
        assertEquals(DateTime.now().toString(ReportMaker.DATE_PATTERN_YYMMDD_HHMM)
                        + "_"
                        + Template.TEXT.getReportName(), report.getName());
    }

}
