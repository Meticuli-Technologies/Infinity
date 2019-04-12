package com.meti.lib.log;

import com.meti.lib.event.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Console extends Component<ConsoleEvent, ConsoleRecord> {
    private final Logger logger;

    public Console() {
        this.logger = Logger.getLogger(Console.class.getSimpleName());
    }

    public void log(Level level, Exception exception) {
        log(level, null, exception);
    }

    public void log(Level level, String message, Exception exception) {
        StringBuilder builder = new StringBuilder();
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

        eventManager.fire(ConsoleEvent.ON_LOG, new ConsoleRecord(level, builder.toString()));
        logger.log(level, builder.toString());
    }

    public void log(Level level, String message) {
        log(level, message, null);
    }
}
