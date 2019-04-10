package com.meti.lib.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static String joinStackTrace(Stream<? extends Throwable> throwables) {
        return throwables.map(ExceptionUtil::toStackTrace)
                .collect(Collectors.joining("\n"));
    }

    private static String toStackTrace(Throwable throwable) {
        StringWriter writer = new StringWriter();
        throwable.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }
}
