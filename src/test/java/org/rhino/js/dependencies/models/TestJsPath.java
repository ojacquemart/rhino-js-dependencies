package org.rhino.js.dependencies.models;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class TestJsPath {

    private JsPath pathOfFoo;

    @Before
    public void setUp() {
        pathOfFoo = new JsPath("foo");
    }

    @Test
    public void testEquals() {
        assertFalse(pathOfFoo.equals(null));
        assertFalse(pathOfFoo.equals("foo"));
        assertFalse(pathOfFoo.equals(new JsPath("bar")));

        assertTrue(pathOfFoo.equals(pathOfFoo));
    }

    @Test
    public void testHashCode() {
        assertFalse(pathOfFoo.hashCode() == "foo".hashCode());
        assertFalse(pathOfFoo.hashCode() == new JsPath("bar").hashCode());
        assertTrue(pathOfFoo.hashCode() == new JsPath("foo").hashCode());
    }

    @Test
    public void testCompareTo() {
        List<JsPath> paths = Lists.newArrayList(pathOfFoo, new JsPath("bar"));
        Collections.sort(paths);

        // Bar must be in first position after sort.
        assertEquals("bar", paths.get(0).getDir());
    }

}
