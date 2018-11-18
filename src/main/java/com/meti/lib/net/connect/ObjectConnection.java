package com.meti.lib.net.connect;

import java.io.*;

public abstract class ObjectConnection extends Connection {
    public final ObjectInputStream objectInputStream;
    public final ObjectOutputStream objectOutputStream;

    ObjectConnection(InputStream inputStream, OutputStream outputStream) throws IOException {
        super(inputStream, outputStream);

        //we reverse the order because the ObjectInputStream requires a header to be flushed in order for things to be sent back and forth
        this.objectOutputStream = new ObjectOutputStream(outputStream);
        this.objectInputStream = new ObjectInputStream(inputStream);
    }

    @Override
    public void close() throws IOException {
        super.close();

        objectInputStream.close();
        objectOutputStream.close();
    }
}
