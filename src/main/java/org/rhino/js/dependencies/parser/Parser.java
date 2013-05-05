package org.rhino.js.dependencies.parser;

import org.rhino.js.dependencies.models.JsFile;

import java.io.File;
import java.util.List;

public class Parser {

    private List<File> files;
    private FunctionResolver functionResolver;

    public Parser(List<File> files) {
        this.files = files;
    }

    public void parse() {

    }

    public List<JsFile> getJsFiles() {
        return null;
    }


}
