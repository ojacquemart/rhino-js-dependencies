package org.rhino.js.dependencies;

import com.google.common.base.Stopwatch;
import org.rhino.js.dependencies.conf.ConfigurationReader;
import org.rhino.js.dependencies.report.ReportMaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * org.rhino.js.dependencies.Main class.
 */
public final class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static final ConfigurationReader CONFIG_READER = ConfigurationReader.load();

    private Main() {}

    public static void main(String[] args) {
        LOGGER.info("Running main....");

        Stopwatch timer = new Stopwatch();
        timer.start();

        ReportMaker reportMaker = new ReportMaker(CONFIG_READER.getProjectName());
        reportMaker.setOutputDir(CONFIG_READER.getOutputDir())
                .prepareData(CONFIG_READER.getJsDir())
                .setTemplate(CONFIG_READER.getTemplateType())
                .generate();

        timer.stop();
        LOGGER.info("Total time = {} ms", timer.elapsed(TimeUnit.MILLISECONDS));
    }

}
