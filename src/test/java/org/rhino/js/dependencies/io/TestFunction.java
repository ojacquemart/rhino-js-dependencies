package org.rhino.js.dependencies.io;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestFunction {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNameNull() {
        new Function(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorPrototypeNotNullAndNamNull() {
        new Function("type", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorPrototypeAndNameNulls() {
        new Function(null, null);
    }

    @Test
    public void testFormatTypeAndName() {
        Function funcName = new Function("foo", "bar");
        assertEquals("foo#bar", funcName.getName());
    }

    @Test
    public void testEquals() {
        // Function without type.
        Function foo = Function.newInstance("foo");

        assertFalse(foo.equals(null));
        assertFalse(foo.equals("foo"));
        assertFalse("foo".equals(foo));
        assertFalse(foo.equals(Function.newInstance("bar")));

        assertTrue(foo.equals(foo));
        assertTrue(foo.equals(Function.newInstance("foo")));

        // Function with type.
        Function fooType = Function.newInstance("type", "foo");
        assertFalse(fooType.equals(foo));
        assertFalse(foo.equals(fooType));
        assertFalse(fooType.equals(Function.newInstance("type", "bar")));
        assertFalse(fooType.equals(Function.newInstance("bar", "type")));

        assertTrue(fooType.equals(Function.newInstance("type", "foo")));
    }

    @Test
    public void testHashcode() {
        Function foo = Function.newInstance("foo");
        assertFalse(foo.hashCode() == "foo".hashCode());
        assertFalse(foo.hashCode() == Function.newInstance("bar").hashCode());
        assertTrue(foo.hashCode() == Function.newInstance("foo").hashCode());

        Function fooType = Function.newInstance("type", "foo");
        assertFalse(fooType.hashCode() == Function.newInstance("foo").hashCode());
        assertFalse(fooType.hashCode() == Function.newInstance("foo", "bar").hashCode());
        assertTrue(fooType.hashCode() == Function.newInstance("type", "foo").hashCode());
    }
}
