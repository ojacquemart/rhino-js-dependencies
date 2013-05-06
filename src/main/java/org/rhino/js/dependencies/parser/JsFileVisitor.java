package org.rhino.js.dependencies.parser;

import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.TreeSet;

public class JsFileVisitor implements NodeVisitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsFileVisitor.class);

    private Set<String> functions = new TreeSet<>();
    private Set<String> functionCalls = new TreeSet<>();

    private SimpleFunctionVistitor simpleFunctionVisitor = new SimpleFunctionVistitor();
    private PrototypeFunctionVisitor prototypeFunctionVisitor = new PrototypeFunctionVisitor();
    private ObjectFunctionVistor objectFunctionVistor = new ObjectFunctionVistor();
    private FunctionCallVisitor functionCallVisitor = new FunctionCallVisitor();

    private JsFileVisitor() {
    }

    public Set<String> getFunctions() {
        LOGGER.info("Found {} function(s)", functions.size());
        LOGGER.debug("Functions found: {}", functions);

        return functions;
    }

    public Set<String> getFunctionCalls() {
        LOGGER.info("Found {} function calls", functionCalls.size());
        LOGGER.info("Function calls found: {}", functionCalls);

        return functionCalls;
    }

    public static JsFileVisitor newInstance(AstNode root) {
        LOGGER.debug("Visiting root node");

        JsFileVisitor visitor = new JsFileVisitor();
        root.visit(visitor);

        return visitor;
    }

    @Override
    public boolean visit(AstNode node) {
        LOGGER.debug("Node type: {}", Token.typeToName(node.getType()));

        node.visit(simpleFunctionVisitor);
        node.visit(prototypeFunctionVisitor);
        node.visit(objectFunctionVistor);
        node.visit(functionCallVisitor);

        return true;
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

    /**
     * Visitor for simple functions.
     */
    private class SimpleFunctionVistitor implements NodeVisitor {

        @Override
        public boolean visit(AstNode node) {
            if (node.getType() == Token.FUNCTION) {
                FunctionNode functionNode = (FunctionNode) node;
                addFunction(functionNode.getName());

                return true;
            }

            return false;
        }
    }

    /**
     * Visitor for Prototype functions.
     */
    private class PrototypeFunctionVisitor implements NodeVisitor {

        @Override
        public boolean visit(AstNode node) {
            if (node.getType() != Token.EXPR_RESULT) {
                return false;
            }

            ExpressionStatement expr = (ExpressionStatement) node;
            AstNode exprNode = expr.getExpression();
            if (exprNode.getType() == Token.ASSIGN) {
                Assignment asg = (Assignment) exprNode;
                if (asg.getLeft().getType() == Token.GETPROP) {
                    PropertyGet propertyGet = (PropertyGet) asg.getLeft();
                    if (propertyGet.getRight().getType() == Token.NAME) {
                        Name name = (Name) propertyGet.getRight();
                        String functionName = name.getString();
                        String prototypeName = getPrototypeName(name);

                        addFunction(formatIfPrototyName(prototypeName, functionName));
                    }
                }
            }
            return true;
        }

        private String getPrototypeName(Name name) {
            if (isProperty(name.getParent())) {
                PropertyGet parent = (PropertyGet) name.getParent();
                if (isProperty(parent.getLeft())) {
                   PropertyGet left = (PropertyGet) parent.getLeft();
                    if (left.getLeft().getType() == Token.NAME) {
                        return getStringFromName((Name) left.getLeft());
                    }
                }
            }

            return "";
        }

        private boolean isProperty(AstNode node) {
            return node.getType() == Token.GETPROP;
        }

        private String getStringFromName(Name name) {
            return name.getString();
        }

        private String formatIfPrototyName(String prototypeName, String functionName) {
            if (prototypeName.isEmpty()) {
                return functionName;
            }

            return String.format("%s#%s", prototypeName, functionName);
        }
    }

    /**
     * Visitor for object functions declaration.
     */
    private class ObjectFunctionVistor implements NodeVisitor {

        @Override
        public boolean visit(AstNode node) {

            if (node.getType() == Token.FUNCTION && node.getParent().getType() == Token.COLON) {
                ObjectProperty objectProperty = (ObjectProperty) node.getParent();
                if (objectProperty.getLeft().getType() == Token.NAME) {
                    Name functionName = (Name) objectProperty.getLeft();
                    addFunction(functionName);

                    return true;
                }
            }

            return false;
        }
    }

    private class FunctionCallVisitor implements NodeVisitor {

        @Override
        public boolean visit(AstNode node) {
            if (node.getType() != Token.CALL) {
                return false;
            }

            FunctionCall call = (FunctionCall) node;
            AstNode target = call.getTarget();
            if (target.getType() == Token.GETPROP) {
                PropertyGet propertyGet = (PropertyGet) target;
                AstNode right = propertyGet.getRight();
                if (right.getType() == Token.NAME) {
                    addFunctionCall((Name) right);
                }
            } else if (target.getType() == Token.NAME) {
                addFunctionCall((Name) target);
            }

            return true;
        }
    }

    private void addFunctionCall(Name name) {
        functionCalls.add(name.getString());
    }

}
