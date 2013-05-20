package org.rhino.js.dependencies.io;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.util.Objects;

public class Function implements Comparable<Function> {

    // Format to display type and name.
    private static final String FORMAT_TYPE_NAME = "%s#%s";

    private String type;
    private String name;

    public Function(String name) {
        checkStringNotNullOrEmpty(name);
        this.name = name;
    }

    public Function(String type, String name) {
        this(name);

        checkStringNotNullOrEmpty(type);
        this.type = type;
    }

    private static void checkStringNotNullOrEmpty(String value) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(value));
    }

    public static Function newInstance(String name) {
        return new Function(name);
    }

    public static Function newInstance(String type, String name) {
        return new Function(type, name);
    }

    public String getName() {
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

        Function that = (Function) o;

        return Objects.equals(name, that.name)
                && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(type, name);
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int compareTo(Function o) {
        return getName().compareTo(o.getName());
    }
}
