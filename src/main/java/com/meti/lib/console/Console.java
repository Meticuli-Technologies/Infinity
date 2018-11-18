package com.meti.lib.console;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class Console {
    private final PrintWriter writer;

    public Console(OutputStream outputStream){
        this(new PrintWriter(outputStream));
    }

    Console(PrintWriter writer) {
        this.writer = writer;
    }

    public String log(Level level, String message) {
        return log(level, message, null);
    }

    private String log(Level level, String message, Exception exception){
        StringBuilder builder = new StringBuilder();

        appendLevel(level, builder);
        appendMessage(message, builder, message != null);
        appendMessage(" ==> ", builder, message != null && exception != null);
        appendException(exception, builder);

        return writeBuilder(builder);
    }

    private String writeBuilder(StringBuilder builder) {
        String string = builder.toString();
        writer.println(string);
        writer.flush();
        return string;
    }

    private String appendException(Exception exception, StringBuilder builder) {
        if(exception != null){
            StringWriter writer = new StringWriter();
            exception.printStackTrace(new PrintWriter(writer));
            builder.append(writer.toString());
        }

        return builder.toString();
    }

    private String appendLevel(Level level, StringBuilder builder) {
        builder.append("[")
                .append(level.toString())
                .append("]: ");
        return builder.toString();
    }

    private String appendMessage(String message, StringBuilder builder, boolean condition) {
        if (condition) {
            builder.append(message);
        }

        return builder.toString();
    }

    public String log(Level level, Exception exception) {
        return log(level, null, exception);
    }
}
