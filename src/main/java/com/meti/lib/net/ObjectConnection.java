package com.meti.lib.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectConnection implements Connection<Object, ObjectInputStream, ObjectOutputStream> {
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    public ObjectConnection(ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    @Override
    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public Object read() throws Exception {
        return inputStream.readObject();
    }

    @Override
    public void write(Object o) throws IOException {
        outputStream.writeObject(o);
    }

    @Override
    public void flush() throws IOException {
        outputStream.flush();
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
        outputStream.close();
    }
}
