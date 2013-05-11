package org.rhino.js.dependencies.report;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.joda.time.DateTime;
import org.rhino.js.dependencies.models.JsFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Report javascript analysis for a given directory with a template using mustache.java.
 *
 * Note: the variables used in mustache.java templates must have a getter withtout the prefix "get".
 */
public class Report {

    public static final String DATE_PATTERN_YYMMDD_HHMM = "YMMdd HHmm";

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
    private boolean flushOut = true;

    /**
     * List of javascript files analysed.
     */
    private List<JsFile> jsFiles;

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public void setRootJsDir(String rootJsDir) {
        this.rootJsDir = rootJsDir;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public void setJsFiles(List<JsFile> jsFiles) {
        this.jsFiles = jsFiles;
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
     * TODO: measure generation time.
     */
    public void generate() {
        try {
            File outputFile = getFile();

            LOGGER.info("Generating report");
            LOGGER.info("\ttemplate used: {}", template.getName());
            LOGGER.info("\toutput directory: {}", outputFile.getAbsolutePath());

            MustacheFactory mf = new DefaultMustacheFactory();
            Mustache mustache = mf.compile(template.getName());
            mustache.execute(new PrintWriter(outputFile), this).flush();
            if (flushOut) {
                mustache.execute(new PrintWriter(System.out), this).flush();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Error during generating report", e);
        }
    }

    public File getFile() {
        if (outputDir == null) {
            File file = new File(rootJsDir + File.separator + getName());

            return file;
        }

        return new File(outputDir + File.separator + getName());
    }

    public String getName() {
        return String.format("%s_%s",
                DateTime.now().toString(DATE_PATTERN_YYMMDD_HHMM),
                template.getReportName());
    }

}
