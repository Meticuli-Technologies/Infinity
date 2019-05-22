package com.meti.lib.handle;

import com.meti.lib.source.ReadableSource;

import java.io.ObjectInputStream;

public interface Handler<T, R extends ReadableSource<ObjectInputStream>> {
    void handle(T token, R readable);
}
