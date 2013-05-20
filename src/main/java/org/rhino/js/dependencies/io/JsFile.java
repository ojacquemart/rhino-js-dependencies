package org.rhino.js.dependencies.io;

import com.google.common.base.Preconditions;
import com.google.common.io.Files;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

/**
 * JsFile.
 */
public class JsFile implements JsFileAttribute, Comparable<JsFile> {

    private File file;
    private FileInfo fileInfo = FileInfo.emptyFileInfo();
    private Set<JsFile> usages = new TreeSet<>();

    public JsFile(File file) {
        this.file = file;
    }

    public JsFile(String path) {
        this.file = new File(path);
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public int getNumberOfLoc() {
        return fileInfo.getNumberLoc();
    }

    /**
     * Returns <code>1</code> if the file is minified, else <code>0</code>.
     */
    @Override
    public int getNumberOfMinified() {
        if (Files.getNameWithoutExtension(getName()).endsWith(".min")) {
            return 1;
        }

        return 0;
    }

    public boolean usesFunction(Function funcName) {
       return hasFunctionInSet(fileInfo.getFunctionCalls(), funcName);
    }

    public boolean hasFunction(Function funcName) {
        return hasFunctionInSet(fileInfo.getFunctions(), funcName);
    }

    private boolean hasFunctionInSet(Set<Function> set, Function funcName) {
        if (funcName == null) {
            return false;
        }

        return set != null && set.contains(funcName);
    }

    public File getFile() {
        return file;
    }

    public FileInfo getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(FileInfo fileInfo) {
        Preconditions.checkArgument(fileInfo != null);

        this.fileInfo = fileInfo;
    }

    public Set<Function> getFunctions() {
        return fileInfo.getFunctions();
    }

    public Set<Function> getFunctionCalls() {
        return fileInfo.getFunctionCalls();
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
        return "JsFile(" +  file + ")";
    }

}
