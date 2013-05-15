package org.rhino.js.dependencies.report;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import org.joda.time.DateTime;
import org.rhino.js.dependencies.models.JsFile;
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
public class Report {

    public static final String DATE_PATTERN_YYMMDD_HHMM = "YMMdd_HHmm";

    private static final Logger LOGGER = LoggerFactory.getLogger(Report.class);

    /**
     * Output directory to store the report.
     */
    private String outputDir;

    /**
     * The javascript root directory analysed.
     */
    private String rootJsDir;

    /**
     * Default template is Template#TEXT.
     */
    private Template template = Template.TEXT;

    /**
     * By default, flush in sysout is enabled.
     */
    private boolean forTest = false;

    /**
     * List of javascript files analysed.
     */
    private List<JsFile> jsFiles;

    public Report setOutputDir(String outputDir) {
        this.outputDir = outputDir;
        return this;
    }

    public Report setRootJsDir(String rootJsDir) {
        Preconditions.checkArgument(rootJsDir != null);
        this.rootJsDir = rootJsDir;
        return this;
    }

    public Report setTemplate(Template template) {
        Preconditions.checkArgument(template != null);
        this.template = template;
        return this;
    }

    public Report setJsFiles(List<JsFile> jsFiles) {
        Preconditions.checkArgument(jsFiles != null);
        this.jsFiles = jsFiles;
        return this;
    }

    public void setJustForTest(boolean forTest) {
        this.forTest = forTest;
    }

    // Mustache getters without prefixes.

    public List<JsFile> jsFiles() {
        return jsFiles;
    }

    public String rootJsDir() {
        return rootJsDir;
    }

    public int nbFiles() {
        if (jsFiles != null) {
            return jsFiles.size();
        }
        return 0;
    }

    public String date() {
        return DateTime.now().toString(DATE_PATTERN_YYMMDD_HHMM);
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
            if (forTest) {
                mustache.execute(new PrintWriter(System.out), this).flush();
            } else {
                File outputFile = getFile();
                LOGGER.info("\toutput directory: {}", outputFile.getAbsolutePath());

                mustache.execute(new PrintWriter(outputFile), this).flush();
            }

            stopwatch.stop();
            LOGGER.info("Report generated in {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        } catch (IOException e) {
            throw new IllegalStateException("Error during generating report", e);
        }
    }

    public File getFile() {
        return new File(getBaseDir() + File.separator + getName());
    }

    public String getBaseDir() {
        if (outputDir == null) {
            return rootJsDir;
        }

        return outputDir;
    }

    public String getName() {
        return String.format("%s_%s",
                DateTime.now().toString(DATE_PATTERN_YYMMDD_HHMM),
                template.getReportName());
    }

}
