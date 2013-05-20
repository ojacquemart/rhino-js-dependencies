package org.rhino.js.dependencies.ast;

import com.google.common.base.Strings;
import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Function call visitor.
 */
public class FunctionCallVisitor extends FunctionVisitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(FunctionCallVisitor.class);

    private final Map<String, String> variableNamesByType = new HashMap<>();

    @Override
    public boolean visit(AstNode node) {
        // Try to guess the types of function calls.
        if (node.getParent() != null && node.getParent().getType() == Token.VAR){
            storeDefinableTypes(node.getParent());
        }

        if (node.getType() != Token.CALL) {
            return false;
        }

        // Simple function calls are in target.
        AstNode target = ((FunctionCall) node).getTarget();
        if (target instanceof Name) {
            return addElement(target);
        }
        // Either way, a function call be called from different ways...
        if (target instanceof PropertyGet) {
            return checkPossibleCalls((PropertyGet) target);
        }

        return true;
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
            if (initializer.getTarget().getType() == Token.NAME) {
                Name functionName = (Name) initializer.getTarget();

                variableNamesByType.put(variableName.getString(), functionName.getString());
            }
        }
    }

    private boolean checkPossibleCalls(PropertyGet propGet) {
        if (isPrototypeStaticCall(propGet)) {
            return false;
        }
        if (isJqueryPluginFunctionCall(propGet)) {
            return false;
        }

        AstNode right = propGet.getRight();
        if (right.getType() == Token.NAME) {
            return tryToDefineTypedFunctionCall(propGet);
        }

        return true;
    }

    /**
     * Specific method to get a method call from a static prototype.
     * Example: <code>Proto.static();</code>
     *
     * @param propGet the property.
     * @return <code>true</code> if we should continue to visit the children
     *         <code>false</code> if we have found a static prototype method call.
     */
    private boolean isPrototypeStaticCall(PropertyGet propGet) {
        if (propGet.getLeft() instanceof Name && propGet.getRight() instanceof Name) {
            String maybePrototypeName = Strings.nullToEmpty(propGet.getLeft().getString());
            if (startsWithUpperCase(maybePrototypeName)) {
                return !addElement(maybePrototypeName, propGet.getRight().getString());
            }
        }

        return false;
    }

    private boolean startsWithUpperCase(String value) {
        String maybePrototypeName = Strings.nullToEmpty(value);
        if (maybePrototypeName.isEmpty()) {
            return false;
        }

        return (Character.isUpperCase(maybePrototypeName.charAt(0)));
    }

    private boolean isJqueryPluginFunctionCall(PropertyGet propertyGet) {
        if (propertyGet.getLeft() instanceof FunctionCall && propertyGet.getRight() instanceof Name) {
            FunctionCall funcCall = (FunctionCall) propertyGet.getLeft();
            if (funcCall.getTarget() instanceof Name && "$".equals(funcCall.getTarget().getString())) {
                return !addElement("$", propertyGet.getRight().getString());
            }
        }

        return false;
    }

    private boolean tryToDefineTypedFunctionCall(PropertyGet propertyGet) {
        String functionName = propertyGet.getRight().getString();
        LOGGER.debug("Try to get defined type of function {}", functionName);

        String variableName = getVariableName(propertyGet);
        if (variableName != null) {
            addFunctionCallByInstance(variableName, functionName);
        } else {
            addElement(functionName);
        }

        return false;
    }

    /**
     * Get variable name from a property.
     * @param propertyGet the property.
     * @return the variable name if found else null.
     */
    private String getVariableName(PropertyGet propertyGet) {
        if (propertyGet.getLeft().getType() == Token.NAME) {
            return propertyGet.getLeft().getString();
        }

        return null;
    }

    private void addFunctionCallByInstance(String variableName, String functionName) {
        String instanceType = variableNamesByType.get(variableName);
        if (instanceType != null) {
            addElement(instanceType, functionName);
        }  else {
            addElement(functionName);
        }
    }

}