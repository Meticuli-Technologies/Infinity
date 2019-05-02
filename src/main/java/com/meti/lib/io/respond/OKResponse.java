package com.meti.lib.io.respond;

public class OKResponse implements Response {
    private final String value;

    public OKResponse(String value) {
        this.value = value;
    }

    @Override
    public Exception getException() {
        return null;
    }

    @Override
    public String getValue() {
        return value;
    }
}
