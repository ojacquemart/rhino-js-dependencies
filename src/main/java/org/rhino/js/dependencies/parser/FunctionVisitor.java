package org.rhino.js.dependencies.parser;

import org.mozilla.javascript.ast.Name;
import org.mozilla.javascript.ast.NodeVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.TreeSet;

/**
 * Abstract function visitor.
 */
public abstract class FunctionVisitor implements NodeVisitor {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final Set<String> functions = new TreeSet<>();

    public void clear() {
        functions.clear();
    }

    protected void addFunction(Name name) {
        addFunction(name.getString());
    }

    protected void addFunction(String functionName) {
        if (!functionName.isEmpty()) {
            LOGGER.debug("Add function: {}", functionName);
            functions.add(functionName);
        }
    }

    public Set<String> getFunctions() {
        LOGGER.info("Found {} function(s)", functions.size());
        LOGGER.debug("Functions found: {}", functions);

        return functions;
    }
}
