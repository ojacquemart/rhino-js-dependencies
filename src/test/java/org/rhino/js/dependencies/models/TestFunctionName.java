package org.rhino.js.dependencies.models;

import org.junit.Test;
import org.rhino.js.dependencies.models.FunctionName;

import static org.junit.Assert.*;

public class TestFunctionName {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNameNull() {
        new FunctionName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorPrototypeNotNullAndNamNull() {
        new FunctionName("type", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorPrototypeAndNameNulls() {
        new FunctionName(null, null);
    }

    @Test
    public void testFormatTypeAndName() {
        FunctionName funcName = new FunctionName("foo", "bar");
        assertEquals("foo#bar", funcName.getString());
    }

    @Test
    public void testEquals() {
        // Function without type.
        FunctionName foo = FunctionName.newInstance("foo");

        assertFalse(foo.equals(null));
        assertFalse(foo.equals("foo"));
        assertFalse("foo".equals(foo));
        assertFalse(foo.equals(FunctionName.newInstance("bar")));

        assertTrue(foo.equals(foo));
        assertTrue(foo.equals(FunctionName.newInstance("foo")));

        // Function with type.
        FunctionName fooType = FunctionName.newInstance("type", "foo");
        assertFalse(fooType.equals(foo));
        assertFalse(foo.equals(fooType));
        assertFalse(fooType.equals(FunctionName.newInstance("type", "bar")));
        assertFalse(fooType.equals(FunctionName.newInstance("bar", "type")));

        assertTrue(fooType.equals(FunctionName.newInstance("type", "foo")));
    }

    @Test
    public void testHashcode() {
        FunctionName foo = FunctionName.newInstance("foo");
        assertFalse(foo.hashCode() == "foo".hashCode());
        assertFalse(foo.hashCode() == FunctionName.newInstance("bar").hashCode());
        assertTrue(foo.hashCode() == FunctionName.newInstance("foo").hashCode());

        FunctionName fooType = FunctionName.newInstance("type", "foo");
        assertFalse(fooType.hashCode() == FunctionName.newInstance("foo").hashCode());
        assertFalse(fooType.hashCode() == FunctionName.newInstance("foo", "bar").hashCode());
        assertTrue(fooType.hashCode() == FunctionName.newInstance("type", "foo").hashCode());
    }
}
