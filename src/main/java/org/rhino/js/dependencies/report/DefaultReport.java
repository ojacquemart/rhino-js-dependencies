package org.rhino.js.dependencies.report;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.rhino.js.dependencies.io.JsPath;
import org.rhino.js.dependencies.io.JsPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Default report implementation.
 */
public class DefaultReport implements Report {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultReport.class);

    public static final String DATE_PATTERN_YYMMDD_HHMM = "YMMdd";

    private String projectName;

    private String rootJsDir;

    private List<JsPath> paths;

    @Override
    public String getProjectName() {
        return projectName;
    }

    @Override
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String getDate() {
        return DateTime.now().toString(DATE_PATTERN_YYMMDD_HHMM);
    }

    @Override
    public String getRootJsDir() {
        return rootJsDir;
    }

    @Override
    public void setRootJsDir(String rootJsDir) {
        this.rootJsDir = rootJsDir;
    }

    @Override
    public List<JsPath> getPaths() {
        return paths;
    }

    @Override
    public void setPaths(List<JsPath> paths) {
        this.paths = paths;
    }

    @Override
    public int getNumberOfLoc() {
        return JsPaths.getTotalOfLoc(paths);
    }

    @Override
    public int getNumberOfFiles() {
        return JsPaths.getNumberOfFiles(paths);
    }

    @Override
    public int getNumberOfMinifiedFiles() {
        return JsPaths.getNumberOfMinifiedFiles(paths);
    }

    @Override
    public String toJson() {
        try {
            LOGGER.debug("Generate json jackson object");

            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error during json processing", e);

            return "";
        }
    }
}
