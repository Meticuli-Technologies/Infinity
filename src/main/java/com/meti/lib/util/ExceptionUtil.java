package com.meti.lib.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {
    public static String stackTraceString(Exception exception) {
        StringWriter writer = new StringWriter();
        exception.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }
}
