package org.rhino.js.dependencies.conf;

import com.typesafe.config.ConfigException;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestConfigurationReader {

    private static final String SRC_TEST_RESOURCES = "src/test/resources/";
    private static final ConfigurationReader CONF_APP_DEFAULTS = ConfigurationReader.load(SRC_TEST_RESOURCES + "conf/applicationDefaults.conf");
    private static final ConfigurationReader CONF_APP_CUSTOMS = ConfigurationReader.load(SRC_TEST_RESOURCES + "conf/applicationCustoms.conf");
    private static final ConfigurationReader CONF_APP_MISSINGS = ConfigurationReader.load(SRC_TEST_RESOURCES + "conf/applicationMissings.conf");

    @Test
    public void testGetProjectName() throws Exception {
        assertEquals("Default", CONF_APP_DEFAULTS.getProjectName());
        assertEquals("Custom", CONF_APP_CUSTOMS.getProjectName());
    }

    @Test
    public void testGetJsDir() throws Exception {
        assertEquals("/jsdir/", CONF_APP_DEFAULTS.getJsDir());
    }

    @Test
    public void testGetOutputDir() throws Exception {
        assertEquals("/jsdir/", CONF_APP_DEFAULTS.getOutputDir());
        assertEquals("/outputdir/", CONF_APP_CUSTOMS.getOutputDir());
    }

    @Test
    public void testGetTemplateType() throws Exception {
        assertEquals("txt", CONF_APP_DEFAULTS.getTemplateType());
        assertEquals("html", CONF_APP_CUSTOMS.getTemplateType());
    }

    @Test(expected = ConfigException.Missing.class)
    public void testGetIllegalPropertyMissing() {
        CONF_APP_MISSINGS.getJsDir();
    }
}
