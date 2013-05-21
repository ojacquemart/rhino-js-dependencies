package org.rhino.js.dependencies.conf;

import com.typesafe.config.ConfigException;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestConfigurationReader {

    private static final String SRC_TEST_RESOURCES = "src/test/resources/";
    private static final String APP_DEFAULTS = SRC_TEST_RESOURCES + "conf/applicationDefaults.conf";
    private static final String APP_CUSTOMS = SRC_TEST_RESOURCES + "conf/applicationCustoms.conf";
    private static final String APP_MISSINGS = SRC_TEST_RESOURCES + "conf/applicationMissings.conf";

    private static ConfigurationReader loadDefaults() {
        return ConfigurationReader.load(APP_DEFAULTS);
    }

    private static  ConfigurationReader loadCustoms() {
        return ConfigurationReader.load(APP_CUSTOMS);
    }

    private static  ConfigurationReader loadMissings() {
        return ConfigurationReader.load(APP_MISSINGS);
    }

    @Test
    public void testGetProjectName() throws Exception {
        assertEquals("Default", loadDefaults().getProjectName());
        assertEquals("Custom", loadCustoms().getProjectName());
    }

    @Test
    public void testGetJsDir() throws Exception {
        assertEquals("/jsdir/", loadDefaults().getJsDir());
    }

    @Test
    public void testGetOutputDir() throws Exception {
        assertEquals("/jsdir/", loadDefaults().getOutputDir());
        assertEquals("/outputdir/", loadCustoms().getOutputDir());
    }

    @Test
    public void testGetTemplateType() throws Exception {
        assertEquals("text", loadDefaults().getTemplateType());
        assertEquals("html", loadCustoms().getTemplateType());
    }

    @Test(expected = ConfigException.Missing.class)
    public void testGetIllegalPropertyMissing() {
        loadMissings().getJsDir();
    }
}
