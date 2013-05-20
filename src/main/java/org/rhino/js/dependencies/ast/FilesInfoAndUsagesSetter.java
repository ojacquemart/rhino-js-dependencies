package org.rhino.js.dependencies.ast;

import org.rhino.js.dependencies.io.JsFile;
import org.rhino.js.dependencies.io.JsPath;

import java.util.ArrayList;
import java.util.List;

public final class FilesInfoAndUsagesSetter {

    private static final FilesUsageResolver FILE_USAGE_RESOLVER = new FilesUsageResolver();

    private List<JsPath> paths;
    private List<JsFile> flattenFiles = new ArrayList<>();

    private FilesInfoAndUsagesSetter(List<JsPath> paths) {
        this.paths = paths;
    }

    public static FilesInfoAndUsagesSetter with(List<JsPath> paths) {
        return new FilesInfoAndUsagesSetter(paths);
    }

    public FilesInfoAndUsagesSetter setInfo() {
         for (JsPath path : paths) {
             flattenFiles.addAll(setFileInfo(path.getFiles()));
         }

        return this;
    }

    public List<JsFile> setFileInfo(List<JsFile> jsFiles) {
        for (JsFile eachJsFile : jsFiles) {
            setFileInfo(eachJsFile);
        }

        return jsFiles;
    }


    public void setFileInfo(JsFile jsFile) {
        jsFile.setFileInfo(FileInfoResolver
                .forFile(jsFile.getFile())
                .get());
    }

    public void andUsages() {
        FILE_USAGE_RESOLVER.resolve(flattenFiles);
    }

}
