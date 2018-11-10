package com.meti.lib.util;

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

    public Console(PrintWriter writer) {
        this.writer = writer;
    }

    public String log(Level level, String message, Exception exception){
        StringBuilder builder = new StringBuilder();
        builder.append("[")
                .append(level.toString())
                .append("]: ");

        if(message != null){
            builder.append(message)
                    .append(" ==> ");
        }

        if(exception != null){
            StringWriter writer = new StringWriter();
            exception.printStackTrace(new PrintWriter(writer));
            builder.append(writer.toString());
        }

        String string = builder.toString();
        writer.println(string);
        writer.flush();
        return string;
    }
}
