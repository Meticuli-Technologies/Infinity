package com.meti.lib.util;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/4/2019
 */
class ExceptionUtilTest {

    @Test
    void compound() {
        NullPointerException e0 = new NullPointerException();
        IllegalArgumentException e1 = new IllegalArgumentException();
        String stackTrace0 = ExceptionUtil.write(e0);
        String stackTrace1 = ExceptionUtil.write(e1);

        Exception result = ExceptionUtil.compound(Arrays.asList(e0, e1), "test");
        assertEquals(stackTrace0 + "test" + stackTrace1, result.getMessage());
    }

    @Test
    void compoundDefault() {
        NullPointerException e0 = new NullPointerException();
        IllegalArgumentException e1 = new IllegalArgumentException();
        String stackTrace0 = ExceptionUtil.write(e0);
        String stackTrace1 = ExceptionUtil.write(e1);

        Exception result = ExceptionUtil.compound(Arrays.asList(e0, e1));
        assertEquals(stackTrace0 + "\n\t" + stackTrace1, result.getMessage());
    }

    @Test
    void write() {
        Exception e = new Exception();
        String stackTrace = ExceptionUtil.write(e);

        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        assertEquals(writer.toString(), stackTrace);
    }
}