package com.meti.lib.source;

import java.io.*;

public class ObjectSource extends DelegateSource<ObjectInputStream, ObjectOutputStream> {
    protected ObjectSource(CompoundSource<?, ?> parentSource) {
        super(parentSource);
    }

    @Override
    protected ObjectInputStream build(InputStream inputStream) throws IOException {
        return new ObjectInputStream(inputStream);
    }

    @Override
    protected ObjectOutputStream build(OutputStream outputStream) throws IOException {
        if (this.getInputStream() == null) throw new IllegalStateException("Object input stream cannot be null.");
        return new ObjectOutputStream(outputStream);
    }
}
