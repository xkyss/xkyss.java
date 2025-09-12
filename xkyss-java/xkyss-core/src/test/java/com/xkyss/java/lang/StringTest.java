package com.xkyss.java.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringTest {

    @Test
    public void test_spit_0() {
        String key = "a";
        String[] split = key.split(":");

        Assertions.assertNotNull(split);
        Assertions.assertEquals("a", split[0]);
    }

    @Test
    public void test_spit_1() {
        String key = "s:a";
        String[] split = key.split(":");

        Assertions.assertNotNull(split);
        Assertions.assertEquals("s", split[0]);
        Assertions.assertEquals("a", split[1]);
    }

    @Test
    public void test_spit_2() {
        String key = "s:a:x";
        String[] split = key.split(":");

        Assertions.assertNotNull(split);
        Assertions.assertEquals("s", split[0]);
        Assertions.assertEquals("a", split[1]);
        Assertions.assertEquals("x", split[2]);
    }

    @Test
    public void test_spit_3() {
        String key = "s:a:x";
        String[] split = key.split(":", 3);

        Assertions.assertNotNull(split);
        Assertions.assertEquals("s", split[0]);
        Assertions.assertEquals("a", split[1]);
        Assertions.assertEquals("x", split[2]);
    }

    @Test
    public void test_spit_4() {
        String key = "s:a:x:y";
        String[] split = key.split(":", 3);

        Assertions.assertNotNull(split);
        Assertions.assertEquals("s", split[0]);
        Assertions.assertEquals("a", split[1]);
        Assertions.assertEquals("x:y", split[2]);
    }
}
