package org.rhino.js.dependencies.report;

public enum Template {

    TEXT("report.text.mustache", "txt"),
    HTML("report.html.angular.mustache", "html")
    ;
    private static final String ROOT = "src/main/resources/templates/";

    private String name;
    private String extension;

    Template(String name, String extension) {
        this.name = ROOT + name;
        this.extension = extension;
    }

    public String getName() {
        return name;
    }

    public String getReportName() {
        return "report." + extension;
    }

    public static Template getReportByType(String type) {
        for (Template template : values()) {
            if (template.extension.equals(type)) {
                return template;
            }
        }

        throw new IllegalArgumentException("Illegal template type: " + type);
    }

}
