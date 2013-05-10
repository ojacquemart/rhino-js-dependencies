package org.rhino.js.dependencies.parser;

import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.Name;
import org.mozilla.javascript.ast.PropertyGet;
import org.rhino.js.dependencies.models.FunctionName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JqueryFunctionVisitor extends FunctionVisitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JqueryFunctionVisitor.class);

    @Override
    public boolean visit(AstNode node) {
        // Jquery possible function : $.fn.FUNCTION = function() {};
        // TODO: see another possible declarations.

        if (node instanceof PropertyGet
                && ((PropertyGet) node).getLeft() instanceof PropertyGet
                && ((PropertyGet) node).getRight() instanceof Name) {
            PropertyGet leftProp = (PropertyGet) ((PropertyGet) node).getLeft();
            boolean jqueryTokenFound = hasJqueryTokenInProperty(leftProp);
            if (jqueryTokenFound) {
                LOGGER.debug("Found jquery tokens $ and fn: {}", jqueryTokenFound);

                FunctionName funcName = new FunctionName("$", ((PropertyGet) node).getRight().getString());
                addElement(funcName);
            }
        }

        return true;
    }

    private boolean hasJqueryTokenInProperty(PropertyGet propertyGet) {
        if (propertyGet.getLeft() instanceof Name && propertyGet.getRight() instanceof Name) {
            return "$".equals(propertyGet.getLeft().getString())
                    && "fn".equals(propertyGet.getRight().getString());
        }

        return false;
    }

}
