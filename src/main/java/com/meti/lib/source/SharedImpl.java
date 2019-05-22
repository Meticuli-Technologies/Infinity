package com.meti.lib.source;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
class SharedImpl implements ObjectImpl {
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;

    public SharedImpl(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }

    @Override
    public void flush() throws IOException {
        objectOutputStream.flush();
    }

    @Override
    public Object read() throws IOException, ClassNotFoundException {
        return objectInputStream.readObject();
    }

    @Override
    public void write(Serializable serializable) throws IOException {
        objectOutputStream.writeObject(serializable);
    }
}
