package org.rhino.js.dependencies.models;

import com.google.common.base.Preconditions;

import java.io.File;
import java.util.Collections;
import java.util.Set;

/**
 * JsFile.
 */
public class JsFile implements Comparable<JsFile> {

    private File file;
    private JsFileInfo fileInfo = JsFileInfo.emptyFileInfo();
    private Set<JsFile> usages = Collections.emptySet();

    public JsFile(File file) {
        this.file = file;
    }

    public JsFile(String path) {
        this.file = new File(path);
    }

    public boolean usesFunction(FunctionName funcName) {
       return hasFunctionInSet(fileInfo.getFunctionCalls(), funcName);
    }

    public boolean hasFunction(FunctionName funcName) {
        return hasFunctionInSet(fileInfo.getFunctions(), funcName);
    }

    private boolean hasFunctionInSet(Set<FunctionName> set, FunctionName funcName) {
        if (funcName == null) {
            return false;
        }

        return set != null && set.contains(funcName);
    }

    public File getFile() {
        return file;
    }

    public JsFileInfo getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(JsFileInfo fileInfo) {
        Preconditions.checkArgument(fileInfo != null);

        this.fileInfo = fileInfo;
    }

    public Set<JsFile> getUsages() {
        return usages;
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

        return file != null && file.compareTo(other.getFile()) == 0;
    }

    @Override
    public int hashCode() {
        return file != null ? file.hashCode() : 0;
    }

    @Override
    public int compareTo(JsFile o) {
        return getFile().compareTo(o.getFile());
    }

    @Override
    public String toString() {
        return "JsFile{" +
                "file='" + file + '\'' +
                ", fileInfo=" + fileInfo +
                '}';
    }

}
