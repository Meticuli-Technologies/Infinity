package com.meti.lib;

import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class Console {
    public void log(User user, Level level, Exception exception) {
        log(user, level, null, exception);
    }

    public void log(User user, Level level, String message, Exception exception) {
        StringBuilder builder = new StringBuilder();
        builder.append("[" + user.name + "," + level.name() + "]: ");
        if (message != null) {
            builder.append(message);
        }

        if (message != null && exception != null) {
            builder.append("\n");
        }

        if (exception != null) {
            StringWriter writer = new StringWriter();
            exception.printStackTrace(new PrintWriter(writer));
            builder.append(writer.toString());
        }

        handle(builder.toString());
    }

    public abstract void handle(String message);

    public void log(User user, Level level, String message) {
        log(user, level, message, null);
    }
}
