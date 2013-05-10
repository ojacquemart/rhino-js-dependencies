package org.rhino.js.dependencies.parser;

import org.rhino.js.dependencies.models.JsFile;

import java.util.List;

public class Parser {

    private Parser() {}

    public static void parseAll(List<JsFile> jsFiles) {
        for (JsFile eachJsFile : jsFiles) {
            parse(eachJsFile);
        }
    }

    public static void parse(JsFile jsFile) {
        jsFile.setFileInfo(JsFileInfoResolver
                .forFile(jsFile.getFile())
                .getJsFileInfo());
    }

}
