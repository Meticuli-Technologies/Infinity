package com.meti.lib.net.query;

public class Request implements Query<Update> {
    public final int key;

    public Request(int key) {
        this.key = key;
    }

    @Override
    public Class<Update> getReturnClass() {
        return Update.class;
    }
}
