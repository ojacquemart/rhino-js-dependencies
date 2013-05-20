package org.rhino.js.dependencies.ast;

import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.*;

import java.util.List;

/**
 * Visitor for object functions declaration.
 */
public class ObjectFunctionVisitor extends FunctionVisitor {

    @Override
    public boolean visit(AstNode node) {
        if (node.getType() == Token.OBJECTLIT && node.getParent() != null && node.getParent() instanceof ReturnStatement) {
            ObjectLiteral objectLiteral = (ObjectLiteral) node;

            String objectName = getObjectName(objectLiteral);
            List<ObjectProperty> elements = objectLiteral.getElements();
            for (ObjectProperty eachProperty : elements) {
                if (eachProperty.getLeft() instanceof Name) {
                    String functionName = eachProperty.getLeft().getString();
                    // FIXME: check if objectName is null.
                    addElement(String.format("%s#%s", objectName, functionName));
                }
            }
            return false;

        }

        return true;

    }

    private String getObjectName(ObjectLiteral objectLiteral) {
        // Object literal > Return > Block > FunctionNode

        if (objectLiteral.getParent().getType() == Token.RETURN) {
            ReturnStatement returnStmt = (ReturnStatement) objectLiteral.getParent();
            if (returnStmt.getParent() instanceof Block) {
                Block block = (Block) returnStmt.getParent();
                if (block.getParent() instanceof FunctionNode) {
                    FunctionNode funcNode = (FunctionNode) block.getParent();
                    if (funcNode != null && funcNode.getFunctionName() != null) {
                        return funcNode.getFunctionName().getString();
                    }
                }
            }
        }

        return null;
    }
}
