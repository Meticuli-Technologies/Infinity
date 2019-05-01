package com.meti.lib.util.log;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Exceptions {
    private Exceptions() {
    }

    public static String stackTraceString(Exception exception) {
        StringWriter writer = new StringWriter();
        exception.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }
}
