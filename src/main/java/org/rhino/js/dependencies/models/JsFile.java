package org.rhino.js.dependencies.models;

import java.util.Set;
import java.util.TreeSet;

/**
 * JsFile.
 */
public class JsFile implements Comparable<JsFile> {

    private String path;

    private Set<String> functions = new TreeSet<>();

    private Set<JsFile> references = new TreeSet<>();

    public JsFile(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Set<String> getFunctions() {
        return functions;
    }

    public void setFunctions(Set<String> functions) {
        this.functions = functions;
    }

    public Set<JsFile> getReferences() {
        return references;
    }

    public void setReferences(Set<JsFile> references) {
        this.references = references;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JsFile other = (JsFile) o;

        return path != null && path.compareTo(other.getPath()) == 0;
    }

    @Override
    public int hashCode() {
        return path != null ? path.hashCode() : 0;
    }

    @Override
    public int compareTo(JsFile o) {
        return getPath().compareTo(o.getPath());
    }

    @Override
    public String toString() {
        return "JsFile{" +
                "path='" + path + '\'' +
                ", functions=" + functions +
                ", references=" + references +
                '}';
    }
}
