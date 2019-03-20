package com.meti.lib.respond;

public class CachedResponse<T> implements Response {
    private T cache;
    private Exception exception;

    protected CachedResponse(T cache) {
        this.cache = cache;
    }

    public CachedResponse(Exception exception) {
        this.exception = exception;
    }

    public T getCache() throws Exception {
        check();
        return cache;
    }

    public <R> R getCache(Class<R> rClass) throws Exception {
        return rClass.cast(getCache());
    }

    public void check() throws Exception {
        if (exception != null) {
            throw exception;
        }
    }
}
