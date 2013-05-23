package org.rhino.js.dependencies.report;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import freemarker.template.Configuration;
import org.joda.time.DateTime;
import org.rhino.js.dependencies.ast.FilesInfoManager;
import org.rhino.js.dependencies.io.JsPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private Writer writer;

    public ReportMaker prepareData(String projectName, String jsDir) {
        report = new DefaultReport();
        report.setRootJsDir(jsDir);
        report.setProjectName(projectName);

        List<JsPath> paths = FilesInfoManager.from(jsDir)
                .setFilesInfo()
                .resolveUsages()
                .get();

        report.setPaths(paths);

        return this;
    }

    /**
     * Generate the report.
     * TODO: try to precompile the templates.
     */
    public void generate() {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();

        LOGGER.info("Generating report");
        LOGGER.info("\ttemplate used: {}", template.getName());

        processTemplate();

        stopwatch.stop();
        LOGGER.info("Report generated in {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    private void processTemplate() {
        try (Writer writer = getWriter()) {
            LOGGER.info("Processing template...");

            Stopwatch timer = new Stopwatch();
            timer.start();

            Configuration ftlConfig = new Configuration();
            freemarker.template.Template ftlTemplate = ftlConfig.getTemplate(template.getName());
            ftlTemplate.process(getModel(), writer);

            timer.stop();
            LOGGER.debug("Processing template duration {} ms", timer.elapsed(TimeUnit.MILLISECONDS));

            writer.flush();
        } catch (Exception e) {
            throw new IllegalStateException("Error during processing and writing template", e);
        }
    }

    public Writer getWriter() {
        if (testMode) {
            return new OutputStreamWriter(System.out);
        }

        try {
            return new FileWriter(getFile());
        } catch (IOException e) {
            throw new IllegalStateException("Error during template report FileWriter instanciation " + e);
        }
    }

    public Map<String, Object> getModel() {
        LOGGER.debug("Building model");

        Map<String, Object> model = new HashMap<>();
        model.put("report", report);

        return model;
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

    public ReportMaker setOutputDir(String outputDir) {
        this.outputDir = outputDir;
        return this;
    }

    public ReportMaker setTemplate(String type) {
        Preconditions.checkArgument(type != null);
        this.template = Template.getReportByType(type);
        return this;
    }

    public ReportMaker enableTestMode() {
        this.testMode = true;
        return this;
    }

}
