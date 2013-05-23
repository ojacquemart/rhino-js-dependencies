package org.rhino.js.dependencies.report;

public enum Template {

    TEXT("report.text.ftl", "txt"),
    JSON("report.json.ftl", "json"),
    HTML("report.html.angular.ftl", "html")
    ;

    private static final String ROOT = "src/main/resources/templates/";

    private String name;
    private String type;

    Template(String name, String type) {
        this.name = ROOT + name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getReportName() {
        return "report." + type;
    }

    public static Template getReportByType(String type) {
        for (Template template : values()) {
            if (template.type.equals(type)) {
                return template;
            }
        }

        throw new IllegalArgumentException("Illegal template type: " + type);
    }

}
