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

    public ObjectChannel getChannel(boolean shared) {
        if (shared) {
            return new SharedObjectChannel();
        } else {
            return new UnsharedObjectChannel();
        }
    }

    private class SharedObjectChannel extends SharedChannel {
        public SharedObjectChannel() {
            super(ObjectSource.this.inputStream, ObjectSource.this.outputStream);
        }

        @Override
        public boolean isClosed() {
            return ObjectSource.this.isClosed();
        }

        @Override
        public boolean isOpen() {
            return ObjectSource.this.isOpen();
        }
    }

    private class UnsharedObjectChannel extends UnsharedChannel {
        public UnsharedObjectChannel() {
            super(ObjectSource.this.inputStream, ObjectSource.this.outputStream);
        }

        @Override
        public boolean isClosed() {
            return ObjectSource.this.isClosed();
        }

        @Override
        public boolean isOpen() {
            return ObjectSource.this.isOpen();
        }
    }
}
