package org.rhino.js.dependencies.report;

import org.rhino.js.dependencies.io.JsPath;

import java.util.List;

/**
 * Interface to generate report based on a mustache-java template.
 */
public interface Report {

    /**
     * Returns the project name report.
     */
    String getProjectName();

    /**
     * Sets the project name report.
     */
    void setProjectName(String projectName);

    /**
     * Returns the report date.
     */
    String getDate();

    /**
     * Returns the root javascript dir.
     */
    String getRootJsDir();

    /**
     * Sets the root js dir.
     */
    void setRootJsDir(String rootJsDir);

    /**
     * Returns the javascript paths.
     */
    List<JsPath> getPaths();

    /**
     * Sets the javascript paths.
     */
    void setPaths(List<JsPath> paths);

    /**
     * Returns the total number of lines of code.
     */
    int getNumberOfLoc();

    /**
     * Returns the total number of files.
     */
    int getNumberOfFiles();

    /**
     * Returns the total number of minified files.
     */
    int getNumberOfMinifiedFiles();

}
