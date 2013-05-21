package org.rhino.js.dependencies.report;

import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class TestTemplate {

    @Test
    public void testIfAllTemplatesExist() {
        for (Template eachTemplate : Template.values()) {
            assertTrue(eachTemplate.getName() + " doesn't exist", Files.exists(Paths.get(eachTemplate.getName())));
        }
    }

    @Test
    public void testGetReportName() {
        assertEquals("report.txt", Template.TEXT.getReportName());
        assertEquals("report.html", Template.HTML.getReportName());
    }

    @Test
    public void testGetTemplateByType() {
        assertEquals(Template.TEXT, Template.getReportByType("txt"));
        assertEquals(Template.HTML, Template.getReportByType("html"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetReportByIllegalType() {
        Template.getReportByType("csv");
    }

}
