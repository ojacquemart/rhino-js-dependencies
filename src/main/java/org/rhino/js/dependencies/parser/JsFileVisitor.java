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
        FUNCTION_CALL_VISITOR.clear();
    }

    @Override
    public boolean visit(AstNode node) {
        LOGGER.trace("Node type: {}", Token.typeToName(node.getType()));

        visitByFunctionDeclarations(node);
        visitByFunctionCalls(node);

        return true;
    }

    private void visitByFunctionDeclarations(AstNode node) {
        for (FunctionVisitor eachVisitor : FUNCTION_DECLARATION_VISITOR) {
            node.visit(eachVisitor);
        }
    }

    private void visitByFunctionCalls(AstNode node) {
        node.visit(FUNCTION_CALL_VISITOR);
    }

    public Set<FunctionName> getFunctions() {
        Set<FunctionName> functions = new TreeSet<>();

        for (FunctionVisitor eachVisitor : FUNCTION_DECLARATION_VISITOR) {
            functions.addAll(eachVisitor.getElements());
        }

        LOGGER.debug("Found overall {} functions", functions.size());

        return functions;
    }

    public Set<FunctionName> getFunctionCalls() {
        return FUNCTION_CALL_VISITOR.getElements();
    }

}
