package org.rhino.js.dependencies.conf;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public final class ConfigurationReader implements Configuration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationReader.class);

    private static final String APP_CONF = "conf/application.conf";

    private static final String CONF_PROJECT_NAME       = "project.name";
    private static final String CONF_JS_DIR             = "js.dir";
    private static final String CONF_OUTPUT_DIR         = "output.dir";
    private static final String CONF_TEMPLATE_TYPE      = "template.type";

    private Config config;
    private String resource;

    private ConfigurationReader() {
        this(APP_CONF);
    }

    private ConfigurationReader(String resource) {
        LOGGER.info("Loading configuration from {}", resource);
        this.resource = resource;
        this.config = ConfigFactory.parseFile(new File(resource));
    }

    public static ConfigurationReader load() {
        return new ConfigurationReader();
    }

    public static ConfigurationReader load(String resource) {
        return new ConfigurationReader(resource);
    }

    @Override
    public String getProjectName() {
        return getValue(CONF_PROJECT_NAME);
    }

    @Override
    public String getJsDir() {
        return getValue(CONF_JS_DIR);
    }

    @Override
    public String getOutputDir() {
        return getValueOrElse(CONF_OUTPUT_DIR, getJsDir());
    }

    @Override
    public String getTemplateType() {
        return getValueOrElse(CONF_TEMPLATE_TYPE, "text");
    }

    private String getValueOrElse(String key, String defaultValue) {
        String value = getValue(key);
        if (!value.isEmpty()) {
            return value;
        }

        return defaultValue;
    }

    private String getValue(String key) {
        String value = config.getString(key);
        LOGGER.debug("{}#{}={}", resource, key, value);

        return value;
    }
}
