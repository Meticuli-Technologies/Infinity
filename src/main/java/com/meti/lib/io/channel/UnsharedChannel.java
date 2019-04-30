package com.meti.lib.io.channel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class UnsharedChannel implements ObjectChannel {
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    public UnsharedChannel(ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void flush() throws IOException {
        outputStream.flush();
    }

    @Override
    public Object read() throws IOException, ClassNotFoundException {
        return inputStream.readUnshared();
    }

    @Override
    public void write(Object object) throws IOException {
        outputStream.writeUnshared(object);
    }
}
