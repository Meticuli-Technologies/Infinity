package com.meti.lib.net;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.meti.lib.util.CollectionUtil.toSingle;

class Processor implements Callable<Void> {
    private final Client client;
    private final Map<Predicate<Object>, Function<Object, Object>> resultMapper;

    public Processor(Client client, Map<Predicate<Object>, Function<Object, Object>> resultMapper) {
        this.client = client;
        this.resultMapper = resultMapper;
    }

    @Override
    public Void call() throws Exception {
        ObjectChannel channel = client.sharedChannel();
        while (client.isOpen()) {
            nextToken(channel);
        }
        return null;
    }

    public void nextToken(ObjectChannel channel) throws IOException, ClassNotFoundException {
        Object result = process(channel.read());
        channel.write(result);
        channel.flush();
    }

    public Object process(Object token) {
        return toSingle(resultMapper.keySet()
                .stream()
                .filter(predicate -> predicate.test(token))
                .map(resultMapper::get).map(objectObjectFunction -> objectObjectFunction.apply(token))
                .collect(Collectors.toSet()));
    }
}
