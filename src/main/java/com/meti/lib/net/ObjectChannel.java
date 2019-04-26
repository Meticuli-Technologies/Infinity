package com.meti.lib.net;

import java.io.IOException;

public interface ObjectChannel {
    void flush() throws IOException;

    Object read() throws IOException, ClassNotFoundException;

    void write(Object object) throws IOException;
}
