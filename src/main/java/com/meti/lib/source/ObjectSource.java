package com.meti.lib.source;

import java.io.*;

public class ObjectSource extends DelegateSource<ObjectInputStream, ObjectOutputStream> implements ObjectSourceImpl {
    private ObjectSource(CompoundSource<?, ?> parentSource) {
        super(parentSource);
    }

    @Override
    public ObjectImpl getImplementation(boolean shared) throws IOException {
        ObjectImpl implementation;
        if (shared) implementation = new SharedImpl(getInputStream(), getOutputStream());
        else implementation = new UnsharedImpl(getInputStream(), getOutputStream());
        return implementation;
    }

    @Override
    protected ObjectInputStream build(InputStream inputStream) throws IOException {
        if (this.getOutputStream() == null) throw new IllegalStateException("Object output stream cannot be null.");
        return new ObjectInputStream(inputStream);
    }

    @Override
    protected ObjectOutputStream build(OutputStream outputStream) throws IOException {
        return new ObjectOutputStream(outputStream);
    }

    public static ObjectSourceImpl from(CompoundSource<?, ?> parentSource) {
        return new ObjectSource(parentSource);
    }
}
