package com.meti.lib.handle;

import com.meti.lib.source.ReadableSource;

import java.io.ObjectInputStream;

public interface Handler<T, R extends ReadableSource<ObjectInputStream>> {
    boolean canHandle(T token);
    void handle(T token, R readable);
}
