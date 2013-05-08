package org.rhino.js.dependencies.parser;

import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Function call visitor.
 */
public class FunctionCallVisitor implements Clearable, NodeVisitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(FunctionCallVisitor.class);

    private final Set<String> functionCalls = new TreeSet<>();
    private final Map<String, String> variableNamesByType = new HashMap<>();

    @Override
    public boolean visit(AstNode node) {
        // Try to guess the function calls type.
        if (node.getParent() != null && node.getParent().getType() == Token.VAR){
            storeDefinableTypes(node.getParent());
        }

        if (node.getType() != Token.CALL) {
            return false;
        }

        // Find functions call and try to get the defined type of function.
        AstNode target = ((FunctionCall) node).getTarget();
        if (target.getType() == Token.NAME) {
            addFunctionCall((Name) target);
        }
        else if (target.getType() == Token.GETPROP) {
            tryToGetDefinedTypeByVariableName((PropertyGet) target);
        }

        return true;
    }

    private void tryToGetDefinedTypeByVariableName(PropertyGet propertyGet) {
        AstNode right = propertyGet.getRight();
        if (right.getType() == Token.NAME) {
            String functionName = right.getString();
            LOGGER.debug("Try to get defined type of function {}", functionName);
            String variableName = getVariableName(propertyGet);
            if (variableName != null) {
                addFunctionCallByInstance(variableName, functionName);
            } else {
                functionCalls.add(functionName);
            }
        }
    }

    private void addFunctionCallByInstance(String variableName, String functionName) {
        String instanceType = variableNamesByType.get(variableName);
        if (instanceType != null) {
            functionCalls.add(String.format("%s#%s", instanceType, functionName));
        }  else {
            functionCalls.add(functionName);
        }
    }

    private String getVariableName(PropertyGet propertyGet) {
        if (propertyGet.getLeft().getType() == Token.NAME) {
            return propertyGet.getLeft().getString();
        }

        return null;
    }

    /**
     * Guess the node type by its VariableDeclaration or VariableInitializer type.
     * It stores the variable names and the type in #variableNamesByType.
     *
     * @param varNode the node of type Token#VAR.
     * @return <code>true</code> if the node type has been guessed else <code>false</code>.1
     */
    private boolean storeDefinableTypes(AstNode varNode) {
        if (varNode instanceof VariableDeclaration) {
            VariableDeclaration varDeclaration = (VariableDeclaration) varNode;
            for (VariableInitializer eachVarInitializer : varDeclaration.getVariables()) {
                handleVariableInitializer(eachVarInitializer);
            }
        }

        if (varNode instanceof VariableInitializer) {
            handleVariableInitializer((VariableInitializer) varNode);
        }

        return true;

    }

    /**
     * Check if the parameter is of type FunctionCall and then puts the variable name and its type in #variableNamesByType.
     *
     * @param varInitializer
     */
    private void handleVariableInitializer(VariableInitializer varInitializer) {
        if (varInitializer.getInitializer() instanceof FunctionCall) {
            Name variableName = (Name) varInitializer.getTarget();

            FunctionCall initializer = (FunctionCall) varInitializer.getInitializer();
            Name functionName = (Name) initializer.getTarget();

            variableNamesByType.put(variableName.getString(), functionName.getString());
        }
    }

    public void clear() {
        functionCalls.clear();
    }

    private void addFunctionCall(Name name) {
        functionCalls.add(name.getString());
    }

    public Set<String> getFunctionCalls() {
        LOGGER.info("Found {} function calls", functionCalls.size());
        LOGGER.debug("Function calls found: {}", functionCalls);

        return functionCalls;
    }

}