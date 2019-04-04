package com.meti.lib.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/4/2019
 */
public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static String writeException(Exception e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }
}
