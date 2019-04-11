package com.meti;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class State extends ArrayList<Object> {
    public <T> T byClassToSingle(Class<T> tClass){
        return CollectionUtil.toSingle(stream()
                .filter(o -> tClass.isAssignableFrom(o.getClass()))
                .map(tClass::cast)
                .collect(Collectors.toList()));
    }
}
