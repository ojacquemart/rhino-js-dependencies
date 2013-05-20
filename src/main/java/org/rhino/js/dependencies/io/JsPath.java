package org.rhino.js.dependencies.io;

import java.util.ArrayList;
import java.util.List;

public class JsPath implements JsFileAttribute, Comparable<JsPath> {

    private final String dir;
    private final List<JsFile> files;

    public JsPath(String dir) {
        this(dir, new ArrayList<JsFile>());
    }

    public JsPath(String dir, List<JsFile> files) {
        this.dir = dir;
        this.files = files;
    }

    public String getDir() {
        return dir;
    }

    @Override
    public String getName() {
        return dir;
    }

    public int getNumberOfFiles() {
        return files.size();
    }

    @Override
    public int getNumberOfLoc() {
        return JsPaths.getTotalOfLoc(files);
    }

    @Override
    public int getNumberOfMinified() {
        return JsPaths.getNumberOfMinifiedFiles(files);
    }

    public List<JsFile> getFiles() {
        return files;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JsPath jsPath = (JsPath) o;

        return dir.equals(jsPath.dir);
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(dir);
    }

    @Override
    public int compareTo(JsPath o) {
        return dir.compareTo(o.dir);
    }

}
