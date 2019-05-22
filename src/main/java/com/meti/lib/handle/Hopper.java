package com.meti.lib.handle;

import com.meti.lib.source.ReadableSource;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.Callable;

public abstract class Hopper<R extends ReadableSource<ObjectInputStream>> implements Callable<R> {
    protected final R readable;

    protected abstract void handle(Object token, R readable);

    Hopper(R readable) {
        this.readable = readable;
    }

    @Override
    public R call() throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = readable.getInputStream()) {
            while (readable.isOpen()) {
                this.handle(inputStream.readObject(), readable);
            }
        }
        return readable;
    }
}
