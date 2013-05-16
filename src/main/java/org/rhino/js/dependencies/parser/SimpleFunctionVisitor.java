package org.rhino.js.dependencies.parser;

import com.google.common.base.Strings;
import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.*;

/**
 * Visitor for simple functions.
 */
public class SimpleFunctionVisitor extends FunctionVisitor {

    @Override
    public boolean visit(AstNode node) {
        if (node.getType() == Token.FUNCTION) {
            FunctionNode functionNode = (FunctionNode) node;

            String functionName = Strings.nullToEmpty(functionNode.getName());
            if (!functionName.isEmpty()) {
                return addElement(functionNode.getName());
            } else {
                return checkUnnamedFunction(functionNode);
            }
        }

        return false;
    }

    /**
     * Checks for the functions without names.
     *
     * @param functionNode the function node.
     * @return <code>true</code> if we should continue to visit children
     *         If <code>false</code>, an unnamed funciton has been found, we should stop to visit.
     */
    private boolean checkUnnamedFunction(FunctionNode functionNode) {
        logger().debug("Check unamed function");

        /**
         * Variable assignment.
         * <code>var hello = function() {};</code>
         */
        if (functionNode.getParent() instanceof VariableInitializer) {
            VariableInitializer var = (VariableInitializer) functionNode.getParent();
            if (var.getTarget() instanceof Name) {
                return addElement(var.getTarget());
            }
        }

        /**
         * Direct assignment.
         * <code>hello = function() {};</code>
         */
        if (functionNode.getParent() instanceof Assignment) {
            Assignment leftAssig = (Assignment) functionNode.getParent();
            if (leftAssig.getLeft() instanceof Name) {
                return addElement(leftAssig.getLeft());
            }
        }

        return true;
    }
}
