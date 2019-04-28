package com.meti.lib.net;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.meti.lib.util.CollectionUtil.toSingle;

public class MappedProcessor extends Processor {
    private final Map<Predicate<Object>, Function<Object, Object>> resultMapper;

    public MappedProcessor(Client client, Map<Predicate<Object>, Function<Object, Object>> resultMapper) {
        super(client);
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

    @Override
    public Object process(Object token) {
        return toSingle(resultMapper.keySet()
                .stream()
                .filter(predicate -> predicate.test(token))
                .map(resultMapper::get).map(objectObjectFunction -> objectObjectFunction.apply(token))
                .collect(Collectors.toSet()));
    }
}
