package com.meti.lib.source;

import java.io.Closeable;
import java.io.OutputStream;

public interface Writable<O extends OutputStream> extends Closeable {
    O getOutputStream();
}
