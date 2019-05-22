package com.meti.lib.source;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public interface ObjectImpl {
    void flush() throws IOException;

    Object read() throws IOException, ClassNotFoundException;

    void write(Serializable serializable) throws IOException;
}
