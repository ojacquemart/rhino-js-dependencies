package org.rhino.js.dependencies.parser;

import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.*;
import org.rhino.js.dependencies.models.FunctionName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class JsFileVisitor implements NodeVisitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsFileVisitor.class);

    private static final List<FunctionVisitor> FUNCTION_DECLARATION_VISITOR = new ArrayList<>();
    private static final FunctionCallVisitor FUNCTION_CALL_VISITOR = new FunctionCallVisitor();

    static {
        FUNCTION_DECLARATION_VISITOR.add(new SimpleFunctionVisitor());
        FUNCTION_DECLARATION_VISITOR.add(new PrototypeFunctionVisitor());
        FUNCTION_DECLARATION_VISITOR.add(new ObjectFunctionVisitor());
        FUNCTION_DECLARATION_VISITOR.add(new JqueryFunctionVisitor());

        // Call visitor must always be in the last position.
        FUNCTION_DECLARATION_VISITOR.add(FUNCTION_CALL_VISITOR);
    }

    private JsFileVisitor() {
    }

    public static JsFileVisitor newInstance(AstNode root) {
        LOGGER.debug("Visiting root node");

        clearVisitors();

        JsFileVisitor visitor = new JsFileVisitor();
        root.visit(visitor);

        return visitor;
    }

    private static void clearVisitors() {
        for (FunctionVisitor eachVisitor : FUNCTION_DECLARATION_VISITOR) {
            eachVisitor.clear();
        }
    }

    @Override
    public boolean visit(AstNode node) {
        LOGGER.trace("Node type: {}", Token.typeToName(node.getType()));

        for (FunctionVisitor eachVisitor : FUNCTION_DECLARATION_VISITOR) {
            node.visit(eachVisitor);
        }

        return true;
    }

    public Set<FunctionName> getFunctions() {
        Set<FunctionName> functions = new TreeSet<>();

        // Get all functions from visitors except #FUNCTION_CALL_VISITOR, which is always in the last position.
        List<FunctionVisitor> visitors = FUNCTION_DECLARATION_VISITOR.subList(0, FUNCTION_DECLARATION_VISITOR.size() - 1);
        for (FunctionVisitor eachVisitor : visitors) {
            functions.addAll(eachVisitor.getElements());
        }

        LOGGER.debug("Found overall {} functions", functions.size());

        return functions;
    }

    public Set<FunctionName> getFunctionCalls() {
        return FUNCTION_CALL_VISITOR.getElements();
    }

}
