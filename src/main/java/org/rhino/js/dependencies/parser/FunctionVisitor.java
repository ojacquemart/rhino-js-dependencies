package org.rhino.js.dependencies.parser;

import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.NodeVisitor;
import org.rhino.js.dependencies.models.FunctionName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.TreeSet;

/**
 * Abstract function visitor.
 */
public abstract class FunctionVisitor implements ContainerFunction, NodeVisitor {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final Set<FunctionName> functions = new TreeSet<>();

    public Logger logger() {
        return LOGGER;
    }

    @Override
    public void clear() {
        functions.clear();
    }

    public void addElement(AstNode name) {
        addElement(name.getString());
    }

    public void addElement(String functionName) {
        if (!functionName.isEmpty()) {
            LOGGER.debug("Add function: {}", functionName);
            addElement(new FunctionName(functionName));
        }
    }

    @Override
    public void addElement(FunctionName funcName) {
        functions.add(funcName);
    }

    @Override
    public Set<FunctionName> getElements() {
        LOGGER.info("Found {} function(s)", functions.size());
        LOGGER.debug("Functions found: {}", functions);

        return functions;
    }
}
