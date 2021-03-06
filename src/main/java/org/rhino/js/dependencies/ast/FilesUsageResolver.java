package org.rhino.js.dependencies.ast;

import com.google.common.base.Preconditions;
import org.rhino.js.dependencies.io.Function;
import org.rhino.js.dependencies.io.JsFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * A simple usage resolver for JsFile.
 * The goal is to resolve dependencies between files.
 */
public final class FilesUsageResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilesUsageResolver.class);

    public FilesUsageResolver() {}

    public void resolve(List<JsFile> files) {
        Preconditions.checkArgument(files != null);

        LOGGER.info("Trying to resolve {} files", files.size());

        for (JsFile eachFile : files) {
            LOGGER.info("Resolving references for {}", eachFile);
            for (JsFile eachOtherFile : files) {
                if (eachFile.equals(eachOtherFile)) {
                    continue;
                }

                resolve(eachFile, eachOtherFile);
            }
        }
    }

    /**
     * It resolves the usage of a File 1 by a File 2.
     * One file uses another if there is at a FunctionName of JsFile#functionCalls in the another JsFile.
     */
    private void resolve(JsFile file1, JsFile file2) {
        LOGGER.trace("Try to resolve usages of file {} in {}", file2, file1);

        Set<Function> file1Functions = file1.getFileInfo().getFunctions();

        for (Function eachFunction : file1Functions) {
            LOGGER.trace("Search for {} usages", eachFunction.getName());

            if (file2.usesFunction(eachFunction)) {
                LOGGER.trace("\t* File {} uses {}:{}", file2, file1, eachFunction);
                file1.getUsages().add(new JsFile(file2.getFile()));

                // file2 use one function of file1, stop looping.
                break;
            }
        }
    }

}
