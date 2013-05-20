package org.rhino.js.dependencies.report;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import org.joda.time.DateTime;
import org.rhino.js.dependencies.ast.FilesInfoAndUsagesSetter;
import org.rhino.js.dependencies.io.JsPath;
import org.rhino.js.dependencies.io.JsPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Report javascript analysis for a given directory with a template using mustache.java.
 *
 * Note: the base variables used in mustache.java templates must have a getter without the "get" prefix.
 */
public class ReportMaker {

    public static final String DATE_PATTERN_YYMMDD_HHMM = "YMMdd_HHmm";

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportMaker.class);

    /**
     * Output directory to store the report.
     */
    private String outputDir;

    /**
     * Default template is Template#TEXT.
     */
    private Template template = Template.TEXT;

    /**
     * The report used in template.
     */
    private Report report;

    /**
     * When test mode is on , the template is flushed in Console.
     */
    private boolean testMode = false;

    public ReportMaker prepareData(String jsDir) {
        report = new DefaultReport();
        report.setRootJsDir(jsDir);

        List<JsPath> paths = JsPaths.getPaths(jsDir);

        FilesInfoAndUsagesSetter infoAndUsagesSetter = FilesInfoAndUsagesSetter.with(paths);
        infoAndUsagesSetter.setInfo().andUsages();

        report.setPaths(paths);

        return this;
    }

    /**
     * Generate the report.
     * TODO: try to precompile the templates.
     */
    public void generate() {
        try {
            Stopwatch stopwatch = new Stopwatch();
            stopwatch.start();

            LOGGER.info("Generating report");
            LOGGER.info("\ttemplate used: {}", template.getName());

            MustacheFactory mf = new DefaultMustacheFactory();
            Mustache mustache = mf.compile(template.getName());
            if (testMode) {
                mustache.execute(new PrintWriter(System.out), report).flush();
            } else {
                File outputFile = getFile();
                LOGGER.info("\toutput directory: {}", outputFile.getAbsolutePath());

                mustache.execute(new PrintWriter(outputFile), report).flush();
            }

            stopwatch.stop();
            LOGGER.info("Report generated in {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        } catch (IOException e) {
            throw new IllegalStateException("Error during generating report", e);
        }
    }

    public ReportMaker setOutputDir(String outputDir) {
        this.outputDir = outputDir;
        return this;
    }

    public ReportMaker setTemplate(Template template) {
        Preconditions.checkArgument(template != null);
        this.template = template;
        return this;
    }

    public void enableTestMode() {
        this.testMode = true;
    }

    public File getFile() {
        return new File(getBaseDir() + File.separator + getName());
    }

    public String getBaseDir() {
        if (outputDir == null) {
            return report.getRootJsDir();
        }

        return outputDir;
    }

    public String getName() {
        return String.format("%s_%s",
                DateTime.now().toString(DATE_PATTERN_YYMMDD_HHMM),
                template.getReportName());
    }

}
