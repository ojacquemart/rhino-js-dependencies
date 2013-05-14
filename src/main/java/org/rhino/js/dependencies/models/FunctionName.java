package org.rhino.js.dependencies.models;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.util.Objects;

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
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        if (this == o) {
            return true;
        }

        FunctionName that = (FunctionName) o;

        return Objects.equals(name, that.name)
                && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(type, name);
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
