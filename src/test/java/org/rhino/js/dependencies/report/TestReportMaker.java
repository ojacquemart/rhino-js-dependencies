package org.rhino.js.dependencies.report;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestReportMaker {

    private ReportMaker reportMaker;

    @Before
    public void setUp() {
        this.reportMaker = new ReportMaker("foo");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetTemplateNull() {
        reportMaker.setTemplate(null);
    }

    @Test
    public void testGenerate() {
        reportMaker.prepareData("src/test")
                .setTemplate("html")
                .enableTestMode()
                .generate();
    }

    @Test
    public void testGenerateAllTemplates() {
        for (Template template : Template.values()) {
            ReportMaker reportMaker = new ReportMaker("foo");
            reportMaker.prepareData("src/test")
                    .setTemplate(template.getType())
                    .enableTestMode()
                    .generate();
        }
    }

    @Test
    public void testGetBaseDirInRootJsDir() {
        String baseDir = reportMaker.prepareData("/jsdir").getBaseDir();
        assertEquals("/jsdir", baseDir);
    }

    @Test
    public void testGetBaseDirInOutputDir() {
        String baseDir = reportMaker.setOutputDir("/outputdir").prepareData("/jsdir").getBaseDir();
        assertEquals("/outputdir", baseDir);
    }

    @Test
    public void testGetName() {
        reportMaker.prepareData("src/test");
        assertEquals(
                ReportMaker.DR_RHINO
                        + "-"
                        + "foo"
                        + "-"
                        + DateTime.now().toString(ReportMaker.DATE_PATTERN_YYMMDD)
                        + "."
                        + Template.HTML.getType(),
                reportMaker.getName());
    }

}
