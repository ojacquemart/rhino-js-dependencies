package org.rhino.js.dependencies.parser;

import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.FunctionNode;

/**
 * Visitor for simple functions.
 */
public class SimpleFunctionVisitor extends FunctionVisitor {

    @Override
    public boolean visit(AstNode node) {
        if (node.getType() == Token.FUNCTION) {
            FunctionNode functionNode = (FunctionNode) node;
            addElement(functionNode.getName());
        }

        return true;
    }
}
