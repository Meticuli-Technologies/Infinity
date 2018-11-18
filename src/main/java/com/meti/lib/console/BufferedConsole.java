package com.meti.lib.console;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class BufferedConsole extends Console {
    private final StringWriter writer;

    private int previousSize;

    public BufferedConsole() {
        this(new StringWriter());
    }

    private BufferedConsole(StringWriter writer) {
        super(new PrintWriter(writer));
        this.writer = writer;

        updateSize(writer);
    }

    private void updateSize(StringWriter writer) {
        this.previousSize = size(writer);
    }

    private int size(StringWriter writer) {
        return writer.getBuffer().length();
    }

    public boolean wasUpdated(){
        boolean result = previousSize != size(writer);
        updateSize(writer);
        return result;
    }

    public StringWriter getWriter() {
        return writer;
    }
}
