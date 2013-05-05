package org.rhino.js.dependencies.parser;

import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.FunctionNode;
import org.mozilla.javascript.ast.NodeVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.TreeSet;

public class FunctionNodeVisitor implements NodeVisitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(FunctionNodeVisitor.class);

    private Set<String> functions = new TreeSet<>();

    private FunctionNodeVisitor() {
    }

    public static Set<String> visitAndGetFunctions(AstNode node) {
        LOGGER.debug("Visiting root node");

        FunctionNodeVisitor visitor = new FunctionNodeVisitor();
        node.visit(visitor);

        return visitor.getFunctions();
    }

    public boolean visit(AstNode node) {
        LOGGER.debug("Node type: {}", Token.typeToName(node.getType()));

        if (isFunction(node)) {
            LOGGER.info("Found function");
            FunctionNode functionNode = (FunctionNode) node;

            functions.add(functionNode.getName());
        }

        return true;
    }

    private boolean isFunction(AstNode node) {
        return node.getType() == Token.FUNCTION;
    }

    public Set<String> getFunctions() {
        LOGGER.info("Found {} function(s)", functions.size());
        LOGGER.debug("Functions found: {}", functions);

        return functions;
    }
}
