package com.meti.lib.net.query;

public class Request implements Query<Update> {
    private final String name;
    private final int key;

    public Request(String name, int key) {
        this.name = name;
        this.key = key;
    }

    @Override
    public Class<Update> getReturnClass() {
        return Update.class;
    }
}
