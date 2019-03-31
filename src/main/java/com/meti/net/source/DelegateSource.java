package com.meti.net.source;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/30/2019
 */
public abstract class DelegateSource<I extends InputStream, O extends OutputStream> implements Source<I, O> {
    private final Source<?, ?> parent;
    private final I inputStream;
    private final O outputStream;

    protected DelegateSource(Source<?, ?> parent) throws IOException {
        this.parent = parent;

        this.inputStream = constructInputStream(parent);
        this.outputStream = constructOutputStream(parent);
    }

    public abstract I constructInputStream(Source<?, ?> source) throws IOException;

    public abstract O constructOutputStream(Source<?, ?> source) throws IOException;

    @Override
    public I getInputStream() {
        return inputStream;
    }

    @Override
    public O getOutputStream() {
        return outputStream;
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
        outputStream.close();

        parent.close();
    }
}
