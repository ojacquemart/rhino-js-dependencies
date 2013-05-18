package org.rhino.js.dependencies.parser;

import org.rhino.js.dependencies.models.JsFile;

import java.util.List;

public class Parser {

    private Parser() {}

    public static List<JsFile> parseAll(List<JsFile> jsFiles) {
        for (JsFile eachJsFile : jsFiles) {
            parse(eachJsFile);
        }
        UsageResolver.resolveUsagesBetween(jsFiles);

        return jsFiles;
    }

    public static void parse(JsFile jsFile) {
        jsFile.setFileInfo(FileInfoResolver
                .forFile(jsFile.getFile())
                .getJsFileInfo());
    }

}
