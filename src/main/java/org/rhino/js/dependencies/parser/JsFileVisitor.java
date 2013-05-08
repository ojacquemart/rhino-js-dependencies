package org.rhino.js.dependencies.parser;

import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class JsFileVisitor implements NodeVisitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsFileVisitor.class);

    private static final List<FunctionVisitor> FUNCTION_DECLARATION_VISITOR = new ArrayList<>();
    private static final FunctionCallVisitor FUNCTION_CALL_VISITOR = new FunctionCallVisitor();

    static {
        FUNCTION_DECLARATION_VISITOR.add(new SimpleFunctionVisitor());
        FUNCTION_DECLARATION_VISITOR.add(new ProtoypeFunctionVisitor());
        FUNCTION_DECLARATION_VISITOR.add(new ObjectFunctionVisitor());
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
        LOGGER.debug("Node type: {}", Token.typeToName(node.getType()));

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

    public Set<String> getFunctions() {
        Set<String> functions = new TreeSet<>();

        for (FunctionVisitor eachVisitor : FUNCTION_DECLARATION_VISITOR) {
            functions.addAll(eachVisitor.getFunctions());
        }

        LOGGER.debug("Found overall {} functions", functions.size());

        return functions;
    }

    public Set<String> getFunctionCalls() {
        return FUNCTION_CALL_VISITOR.getFunctionCalls();
    }

}
