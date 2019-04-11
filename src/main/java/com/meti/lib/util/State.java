package com.meti.lib.util;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class State extends ArrayList<Object> {
    public <T> T byClassToSingle(Class<T> tClass){
        return byClass(tClass)
                .findAny()
                .orElseThrow();
    }

    public <T> Stream<T> byClass(Class<T> tClass) {
        return stream()
                .filter(o -> tClass.isAssignableFrom(o.getClass()))
                .map(tClass::cast);
    }
}
