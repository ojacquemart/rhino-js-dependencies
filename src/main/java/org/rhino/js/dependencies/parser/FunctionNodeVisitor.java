package org.rhino.js.dependencies.parser;

import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.*;
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

        switch (node.getType()) {
            case Token.FUNCTION:
                // Simple function.
                FunctionNode functionNode = (FunctionNode) node;
                addFunction(functionNode.getName());
                break;
            case Token.EXPR_RESULT:
                // Expression result with prototype.
                resolvePrototypeName((ExpressionStatement) node);
                break;
            default:
                break;
        }

        return true;
    }

    private void resolvePrototypeName(ExpressionStatement expr) {
        AstNode exprNode = expr.getExpression();
        if (exprNode.getType() == Token.ASSIGN) {
            Assignment asg = (Assignment) exprNode;
            if (asg.getLeft().getType() == Token.GETPROP) {
                PropertyGet propertyGet = (PropertyGet) asg.getLeft();
                if (propertyGet.getRight().getType() == Token.NAME) {
                    Name name = (Name) propertyGet.getRight();
                    addFunction(name);
                }
            }
        }
    }

    private void addFunction(Name name) {
        addFunction(name.getString());
    }

    private void addFunction(String functionName) {
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
