package com.meti.lib.net;

public interface Updater<T> {
    void update(T obj);

    boolean wasUpdated();
}
