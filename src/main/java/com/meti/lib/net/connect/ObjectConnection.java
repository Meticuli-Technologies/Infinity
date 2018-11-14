package com.meti.lib.net.connect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class ObjectConnection extends Connection {
    public final ObjectInputStream objectInputStream;
    public final ObjectOutputStream objectOutputStream;

    public ObjectConnection() throws IOException {
        this.objectOutputStream = new ObjectOutputStream(getOutputStream());
        this.objectInputStream = new ObjectInputStream(getInputStream());
    }

    @Override
    public void close() throws IOException {
        super.close();

        objectInputStream.close();
        objectOutputStream.close();
    }
}
