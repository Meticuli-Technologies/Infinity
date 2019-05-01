package com.meti.lib.io.source;

import com.meti.lib.io.channel.ObjectChannel;
import com.meti.lib.io.channel.SharedChannel;
import com.meti.lib.io.channel.UnsharedChannel;

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
        SharedObjectChannel() {
            super(ObjectSource.this.inputStream, ObjectSource.this.outputStream);
        }

        @Override
        public boolean isOpen() {
            return ObjectSource.this.isOpen();
        }
    }

    private class UnsharedObjectChannel extends UnsharedChannel {
        UnsharedObjectChannel() {
            super(ObjectSource.this.inputStream, ObjectSource.this.outputStream);
        }

        @Override
        public boolean isOpen() {
            return ObjectSource.this.isOpen();
        }
    }
}
