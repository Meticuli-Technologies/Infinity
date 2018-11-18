package com.meti.lib.convert;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/17/2018
 */
public class DoubleConverter implements Converter<Double> {
    @Override
    public Double apply(Object o) {
        return Double.parseDouble(o.toString());
    }

    @Override
    public boolean test(Object o) {
        try {
            Double.parseDouble(o.toString());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
