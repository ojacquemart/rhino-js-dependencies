package org.rhino.js.dependencies.ast;

import com.google.common.base.Strings;
import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.*;
import org.rhino.js.dependencies.io.Function;

/**
 * Visitor for Prototype functions.
 * This visitor should visit classic prototype functions and static-like functions.
 */
public class PrototypeFunctionVisitor extends FunctionVisitor {

    @Override
    public boolean visit(AstNode node) {
        if (node.getType() != Token.EXPR_RESULT) {
            return false;
        }

        ExpressionStatement expr = (ExpressionStatement) node;
        AstNode exprNode = expr.getExpression();
        if (exprNode.getType() != Token.ASSIGN) {
            return false;
        }

        Assignment asg = (Assignment) exprNode;
        if (asg.getLeft() instanceof PropertyGet) {
            return addPrototypeFunction((PropertyGet) asg.getLeft());
        }

        return false;
    }

    private boolean addPrototypeFunction(PropertyGet propGet) {
        if (propGet.getRight() instanceof Name) {
            String prototypeName = getPrototypeName(propGet.getLeft());
            String functionName = propGet.getRight().getString();

            if (!Strings.isNullOrEmpty(prototypeName) && !Strings.isNullOrEmpty(functionName)) {
                return addElement(getFunctionName(prototypeName, functionName));
            }
        }

        return true;
    }

    private String getPrototypeName(AstNode leftNode) {
        /**
         For prototype static methods declaration:
         <code>Foo.bar = function() {};</code>
         */
        if (leftNode instanceof Name) {
            return leftNode.getString();
        }

        /**
         For classic prototype methods declaration:
         <code>Foo.prototype.bar = function() {};</code>
         */
        if (leftNode instanceof PropertyGet) {
            PropertyGet property = (PropertyGet) leftNode;
            if (property.getLeft() instanceof Name && equalsToPrototype(property.getRight())) {
                return property.getLeft().getString();
            }

        }

        return "";
    }

    private boolean equalsToPrototype(AstNode node) {
        return "prototype".equals(node.getString());
    }

    private String getFunctionName(String prototypeName, String functionName) {
        return new Function(prototypeName, functionName).getName();
    }

}
