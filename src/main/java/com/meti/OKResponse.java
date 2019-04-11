package com.meti;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class OKResponse implements Response {
    private final String value;

    public OKResponse(String value) {
        this.value = value;
    }
}
