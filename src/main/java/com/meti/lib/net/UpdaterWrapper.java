package com.meti.lib.net;

public class UpdaterWrapper<C, T> implements Updater<T> {
    private C previous;
    private C current;

    public void set(C current) {
        this.previous = this.current;
        this.current = current;
    }

    @Override
    public void update(T obj) {
        //TODO: handle C on T
    }

    @Override
    public boolean wasUpdated() {
        boolean result = previous != current;
        previous = current;
        return result;
    }
}
