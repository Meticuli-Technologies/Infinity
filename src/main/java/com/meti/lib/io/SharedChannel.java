package com.meti.lib.io;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SharedChannel implements ObjectChannel {
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    public SharedChannel(ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void flush() throws IOException {
        outputStream.flush();
    }

    @Override
    public Object read() throws IOException, ClassNotFoundException {
        return inputStream.readObject();
    }

    @Override
    public void write(Object object) throws IOException {
        outputStream.writeObject(object);
    }
}
