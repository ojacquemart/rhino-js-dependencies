package org.rhino.js.dependencies.parser;

import org.rhino.js.dependencies.models.FunctionName;

import java.util.Set;

public interface ContainerFunction {

    void clear();

    Set<FunctionName> getElements();

    void addElement(FunctionName function);

}
