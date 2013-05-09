package org.rhino.js.dependencies.parser;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestFunctionName {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNameEmpty() {
        new FunctionName(null);
    }

    @Test
    public void testFormatTypeAndName() {
        FunctionName funcName = new FunctionName("foo", "bar");
        assertEquals("foo#bar", funcName.getString());
    }
}
