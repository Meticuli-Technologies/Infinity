package com.meti.lib.net.token;

public abstract class TypeHandler<T> extends PredicateHandler<Object> {
    private final Class<T> tClass;

    protected TypeHandler(Class<T> tClass) {
        super(o -> tClass.isAssignableFrom(o.getClass()));
        this.tClass = tClass;
    }

    @Override
    public void accept(Object o) {
        acceptCast(tClass.cast(o));
    }

    public abstract void acceptCast(T obj);
}
