package com.meti.asset;

public class Asset {
    private final String name;

    protected Asset(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
