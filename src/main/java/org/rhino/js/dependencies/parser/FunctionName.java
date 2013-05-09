package org.rhino.js.dependencies.parser;

public class FunctionName {

    // Format to display type and name.
    private static final String FORMAT_TYPE_NAME = "%s#%s";

    private String type;
    private String name;

    public FunctionName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Function name cannot be null.");
        }

        this.name = name;
    }

    public FunctionName(String type, String name) {
        this(name);
        this.type = type;
    }

    public String getString() {
        if (type == null) {
            return name;
        }

        return String.format(FORMAT_TYPE_NAME, type, name);
    }
}
