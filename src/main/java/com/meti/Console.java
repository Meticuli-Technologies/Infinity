package com.meti;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/7/2018
 */
public class Console {
    private final Logger logger = Logger.getLogger(getClass().getName());

    public String log(Level level, String msg, Exception e) {
        StringBuilder builder = new StringBuilder();

        if(msg != null){
            builder.append(msg);
        }

        if(msg != null && e != null){
            builder.append("\n");
        }

        if(e != null){
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            builder.append(writer.toString());
        }

        String result = builder.toString();
        logger.log(level, result);
        return result;
    }

    public String log(Level level, String msg){
        return log(level, msg, null);
    }

    public String log(Level level, Exception e){
        return log(level, null, e);
    }

    public Supplier<Consumer<Exception>> getExceptionSupplier(Level supplierLevel) {
        return () -> e -> log(supplierLevel, e);
    }
}
