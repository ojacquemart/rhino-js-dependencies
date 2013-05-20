package org.rhino.js.dependencies;

import org.rhino.js.dependencies.report.ReportMaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * org.rhino.js.dependencies.Main class.
 */
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static final String PROP_JSDIR = "jsdir";
    private static final String PROP_OUTPUTDIR = "outputdir";

    private String jsDir;
    private String outputDir;

    public static void main(String[] args) {
        LOGGER.info("Running main....");

        Main main = new Main();
        if (main.setAndCheckPropDirs()) {
            main.start();
        }
    }

    public void start() {
        ReportMaker reportMaker = new ReportMaker();
        reportMaker.setOutputDir(outputDir)
                .prepareData(jsDir)
                .generate();

    }

    public boolean setAndCheckPropDirs() {
        setDirs();
        if (jsDir == null) {
            LOGGER.warn("Usage: -Djsdir=jsdir [-Doutputdir=outputdir] Main");
            return false;
        }

        return existsDir(PROP_JSDIR, jsDir) && existsDir(PROP_OUTPUTDIR, outputDir);
    }

    public void setDirs() {
        this.jsDir = System.getProperty(PROP_JSDIR);
        LOGGER.debug("jsdir={}", jsDir);

        // Takes jsdir if outputdir is not defined.
        this.outputDir = System.getProperty(PROP_OUTPUTDIR, jsDir);
        LOGGER.debug("outputdir={}", outputDir);
    }

    public static boolean existsDir(String dirName, String dir) {
        if (dir != null && !Files.exists(Paths.get(dir))) {
            LOGGER.warn("{}={} doesn't exist", dirName, dir);
            return false;
        }

        return true;
    }

}
