package com.meti.lib.source;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class DelegateSource<I extends InputStream, O extends OutputStream> implements CompoundSource<I, O> {
    private final CompoundSource<?, ?> parentSource;
    private I inputStream;
    private O outputStream;

    protected DelegateSource(CompoundSource<?, ?> parentSource) {
        this.parentSource = parentSource;
    }

    protected abstract I build(InputStream inputStream) throws IOException;

    protected abstract O build(OutputStream outputStream) throws IOException;

    @Override
    public I getInputStream() throws IOException {
        if (inputStream == null) {
            inputStream = this.build(parentSource.getInputStream());
        }

        return inputStream;
    }

    @Override
    public boolean isClosed() {
        return parentSource.isClosed();
    }

    @Override
    public O getOutputStream() throws IOException {
        if (outputStream == null) {
            outputStream = this.build(parentSource.getOutputStream());
        }

        return outputStream;
    }

    @Override
    public void close() throws IOException {
        parentSource.close();
    }
}
