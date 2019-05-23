package com.meti.app.server;

import com.meti.lib.handle.MappedHandlerImpl;
import com.meti.lib.source.ObjectSourceImpl;
import com.meti.lib.source.ReadableSource;

import java.io.ObjectInputStream;

public interface MappedHandlerProvider {
    MappedHandlerImpl<Object, ? extends ReadableSource<ObjectInputStream>> getHandler();
}
