package org.rhino.js.dependencies.models;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

public class FunctionName implements Comparable<FunctionName> {

    // Format to display type and name.
    private static final String FORMAT_TYPE_NAME = "%s#%s";

    private String type;
    private String name;

    public FunctionName(String name) {
        preCheckStringNotNullOrEmpty(name);

        this.name = name;
    }

    public FunctionName(String type, String name) {
        this(name);
        preCheckStringNotNullOrEmpty(type);
        this.type = type;
    }

    private static void preCheckStringNotNullOrEmpty(String value) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(value));
    }

    public static FunctionName newInstance(String name) {
        return new FunctionName(name);
    }

    public static FunctionName newInstance(String type, String name) {
        return new FunctionName(type, name);
    }

    public String getString() {
        if (type == null) {
            return name;
        }

        // Formats type and name.
        return String.format(FORMAT_TYPE_NAME, type, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FunctionName that = (FunctionName) o;

        if (!name.equals(that.name)) return false;
        if (type != null && that.type != null) return type.equals(that.type);

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + name.hashCode();

        return result;
    }

    @Override
    public String toString() {
        return getString();
    }

    @Override
    public int compareTo(FunctionName o) {
        return getString().compareTo(o.getString());
    }
}
