package com.meti.net;

import com.meti.net.object.ObjectClient;
import com.meti.util.CollectionUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/30/2019
 */
public class MappedProcessor extends Processor {
    protected final Set<Handler<Object>> handlers = new HashSet<>();

    public MappedProcessor(ObjectClient client) {
        super(client);
    }

    @Override
    public Object processToken(Object token) {
        Set<Object> collect = handlers.stream()
                .filter(objectHandler -> objectHandler.test(token))
                .map(objectHandler -> objectHandler.apply(token))
                .collect(Collectors.toSet());
        return CollectionUtil.toSingle(collect);
    }
}
