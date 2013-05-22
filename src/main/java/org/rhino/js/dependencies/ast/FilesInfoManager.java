package org.rhino.js.dependencies.ast;

import org.rhino.js.dependencies.io.JsFile;
import org.rhino.js.dependencies.io.JsPath;
import org.rhino.js.dependencies.io.JsPaths;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager to set files info and resolve usages between javascript files.
 *
 * @see FileInfoResolver
 * @see FilesUsageResolver
 */
public final class FilesInfoManager {

    private static final FilesUsageResolver FILE_USAGE_RESOLVER = new FilesUsageResolver();

    private List<JsPath> paths;
    private List<JsFile> flattenFiles = new ArrayList<>();

    private FilesInfoManager(List<JsPath> paths) {
        this.paths = paths;
    }

    public static FilesInfoManager from(String jsDir) {
        return new FilesInfoManager(JsPaths.getPaths(jsDir));
    }

    public FilesInfoManager setFilesInfo() {
         for (JsPath path : paths) {
             flattenFiles.addAll(setFileInfo(path.getFiles()));
         }
        return this;
    }

    private List<JsFile> setFileInfo(List<JsFile> jsFiles) {
        for (JsFile eachJsFile : jsFiles) {
            setFileInfo(eachJsFile);
        }

        return jsFiles;
    }


    private void setFileInfo(JsFile jsFile) {
        jsFile.setFileInfo(FileInfoResolver
                .forFile(jsFile.getFile())
                .get());
    }

    public FilesInfoManager resolveUsages() {
        FILE_USAGE_RESOLVER.resolve(flattenFiles);
        return this;
    }

    public List<JsPath> get() {
        return paths;
    }

}
