package org.rhino.js.dependencies.report;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class TestTemplate {

    @Test
    public void testIfAllTemplatesExist() {
        for (Template eachTemplate : Template.values()) {
            assertTrue(eachTemplate.getName() + " doesn't exist", new File(eachTemplate.getName()).exists());
        }
    }

    @Test
    public void testGetReportName() {
        assertEquals("report.txt", Template.TEXT.getReportName());
        assertEquals("report.html", Template.HTML.getReportName());
    }



}
