package com.meti.lib.respond;

public class CachedResponse<T> implements Response {
    private T cache;
    private Exception exception;

    CachedResponse(T cache) {
        this.cache = cache;
    }

    public CachedResponse(Exception exception) {
        this.exception = exception;
    }

    public T getCache() throws Exception {
        check();
        return cache;
    }

    private void check() throws Exception {
        if (exception != null) {
            throw exception;
        }
    }
}
