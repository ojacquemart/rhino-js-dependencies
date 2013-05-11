package org.rhino.js.dependencies.report;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class TestTemplate {

    @Test
    public void testAllTemplatesExist() {
        for (Template eachTemplate : Template.values()) {
            assertTrue(eachTemplate.getName() + " doesn't exist", new File(eachTemplate.getName()).exists());
        }
    }

    @Test
    public void testGetNameByExtension() {
        Template template = Template.TEXT;
        assertEquals("report.text.txt", template.getNameByExtension());
    }



}
