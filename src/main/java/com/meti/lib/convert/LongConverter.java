package com.meti.lib.convert;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/17/2018
 */
public class LongConverter implements Converter<Long> {
    @Override
    public Long apply(Object o) {
        return Long.parseLong(o.toString());
    }

    @Override
    public boolean test(Object o) {
        try {
            Long.parseLong(o.toString());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
