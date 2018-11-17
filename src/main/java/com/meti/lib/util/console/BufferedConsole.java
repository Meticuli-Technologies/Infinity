package com.meti.lib.util.console;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class BufferedConsole extends Console {
    private final StringWriter buffer;

    public BufferedConsole() {
        this(new StringWriter());
    }

    public BufferedConsole(StringWriter buffer) {
        super(new PrintWriter(buffer));
        this.buffer = buffer;
    }

    public StringWriter getBuffer() {
        return buffer;
    }
}
