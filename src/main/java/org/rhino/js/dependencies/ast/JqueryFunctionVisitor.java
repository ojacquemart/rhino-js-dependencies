package org.rhino.js.dependencies.ast;

import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.Name;
import org.mozilla.javascript.ast.PropertyGet;
import org.rhino.js.dependencies.io.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JqueryFunctionVisitor extends FunctionVisitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JqueryFunctionVisitor.class);

    @Override
    public boolean visit(AstNode node) {
        /*
         * Jquery possible function :
         * <code>$.fn.FUNCTION = function() {};</code>
         */
        // TODO: see another possible declarations.
        if (isJqueryPluginFunction(node)) {
            LOGGER.debug("Found jquery tokens $.fn.FUNCTION");

            Function funcName = new Function("$", ((PropertyGet) node).getRight().getString());
            return addAndContinueVisit(funcName);
        }

        return true;
    }

    private boolean isJqueryPluginFunction(AstNode node) {
        if (node instanceof PropertyGet
                && ((PropertyGet) node).getLeft() instanceof PropertyGet
                && ((PropertyGet) node).getRight() instanceof Name) {
            PropertyGet leftProp = (PropertyGet) ((PropertyGet) node).getLeft();
            return hasJqueryTokenInProperty(leftProp);
        }

        return false;
    }

    private boolean hasJqueryTokenInProperty(PropertyGet propertyGet) {
        if (propertyGet.getLeft() instanceof Name && propertyGet.getRight() instanceof Name) {
            return "$".equals(propertyGet.getLeft().getString())
                    && "fn".equals(propertyGet.getRight().getString());
        }

        return false;
    }

}
