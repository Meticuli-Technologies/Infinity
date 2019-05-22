package com.meti.lib.net;

import com.meti.lib.source.ReadableSource;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.Callable;

public class Hopper implements Callable<ReadableSource<ObjectInputStream>> {
    private final ReadableSource<ObjectInputStream> readable;

    public Hopper(ReadableSource<ObjectInputStream> readable) {
        this.readable = readable;
    }

    @Override
    public ReadableSource<ObjectInputStream> call() throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = readable.getInputStream()) {
            while (readable.isOpen()) {
                Object token = inputStream.readObject();
                //TODO: handle token
            }
        }
        return readable;
    }
}
