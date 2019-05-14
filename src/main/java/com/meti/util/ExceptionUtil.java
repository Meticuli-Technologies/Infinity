package com.meti.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/14/2019
 */
public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static String stackTraceString(Throwable throwable) {
        StringWriter writer = new StringWriter();
        throwable.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }
}
