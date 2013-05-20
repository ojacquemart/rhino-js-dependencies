package org.rhino.js.dependencies.ast;

import com.google.common.collect.Sets;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.NodeVisitor;
import org.rhino.js.dependencies.io.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.TreeSet;

/**
 * Abstract function visitor with helper methods.
 */
public abstract class FunctionVisitor implements NodeVisitor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * The functions list.
     */
    private Set<Function> functions = new TreeSet<>();

    /**
     * Method to get the class logger.
     *
     * @return the logger.
     */
    public Logger logger() {
        return logger;
    }

    /**
     * Clear the functions list.
     */
    public void clear() {
        functions = Sets.newTreeSet();
    }

    /**
     * Add an element by its node name, supposing that AstNode#getType == Token.NAME.
     *
     * @param node the node
     * @return #see #addElement(String)
     */
    public boolean addElement(AstNode node) {
        return addElement(node.getString());
    }

    /**
     * Add an element by its type and name.
     * @param type the type.
     * @param name the named.
     * @see #addElement(String)
     */
    public boolean addElement(String type, String name) {
        return addAndContinueVisit(new Function(type, name));
    }

    /**
     * Add an element by its string.
     *
     * @param functionName the stringed function name.
     * @return <code>true</code> if the function has not been added
     *         <code>false</code> if the function has been added and then we should stop visiting.
     */
    public boolean addElement(String functionName) {
        // Ignore null or emmpty function name or jquery ($) core function.
        if (functionName != null
                && !functionName.isEmpty()
                && !"$".equals(functionName)) {
            logger.debug("Add function: {}", functionName);
            return addAndContinueVisit(new Function(functionName));
        }

        // Continue visiting.
        return true;
    }

    /**
     * Add a function found from the parsing of a js files.
     *
     * @param funcName the function to add.
     * @return <code>false</code> when an element is added and then stop the visiting of others nodes.
     *         <code>true</code> if we should continue visiting nodes.
     * @see org.mozilla.javascript.ast.NodeVisitor#visit(org.mozilla.javascript.ast.AstNode)
     */
    public boolean addAndContinueVisit(Function funcName) {
        functions.add(funcName);

        return false;
    }

    /**
     * Get the functions list.
     * @return the functions list.
     */
    public Set<Function> getElements() {
        logger.info("Found {} function(s)", functions.size());
        logger.debug("Functions found: {}", functions);

        return functions;
    }
}
