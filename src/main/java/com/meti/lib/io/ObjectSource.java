package com.meti.lib.io;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectSource<S extends Source> extends ParentSource<S> {
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;


    public ObjectSource(S source) throws IOException {
        super(source);

        this.outputStream = new ObjectOutputStream(source.getOutputStream());
        this.inputStream = new ObjectInputStream(source.getInputStream());
    }

    @Override
    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    @Override
    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }
}
