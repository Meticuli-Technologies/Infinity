package com.meti.lib.net;

import com.meti.lib.net.handle.TokenHandler;
import com.meti.lib.net.object.ObjectClient;
import com.meti.lib.util.CollectionUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/30/2019
 */
public class MappedProcessor extends Processor {
    protected final Set<TokenHandler<Object>> tokenHandlers = new HashSet<>();

    public MappedProcessor(ObjectClient client) {
        super(client);
    }

    @Override
    public Object processToken(Object token) {
        Set<Object> collect = tokenHandlers.stream()
                .filter(objectTokenHandler -> objectTokenHandler.test(token))
                .map(objectTokenHandler -> objectTokenHandler.apply(token))
                .collect(Collectors.toSet());
        return CollectionUtil.toSingle(collect).orElse(null);
    }
}
