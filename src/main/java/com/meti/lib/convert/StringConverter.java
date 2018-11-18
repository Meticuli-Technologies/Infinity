package com.meti.lib.convert;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/17/2018
 */
public class StringConverter extends ClassConverter<String> {
    public StringConverter() {
        super(String.class);
    }

    @Override
    public String apply(Object o) {
        return o.toString();
    }
}
