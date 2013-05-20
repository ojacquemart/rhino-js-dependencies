package org.rhino.js.dependencies.report;

import org.joda.time.DateTime;
import org.rhino.js.dependencies.io.JsPath;
import org.rhino.js.dependencies.io.JsPaths;

import java.util.List;

/**
 * Default report implementation.
 */
public class DefaultReport implements Report {

    public static final String DATE_PATTERN_YYMMDD_HHMM = "YMMdd";

    private String rootJsDir;

    private List<JsPath> paths;

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

}
