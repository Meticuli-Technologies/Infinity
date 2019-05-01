package com.meti.lib.io.server;

import com.meti.lib.io.channel.ObjectChannel;
import com.meti.lib.io.source.ObjectSource;
import com.meti.lib.io.source.Source;
import com.meti.lib.io.source.supplier.SourceSupplier;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.meti.lib.util.collect.Collections.computeFromResults;

public class MappedServer<S extends Source, T extends SourceSupplier<S>> extends Server<S, T> {
    //TODO: implement in InfinityServer
    protected final Map<Predicate<Object>, Function<Object, Object>> handlerMap = new HashMap<>();
    private final boolean shared;

    protected MappedServer(T supplier, boolean shared) {
        super(supplier);
        this.shared = shared;
    }

    @Override
    protected void accept(S source) throws IOException, ClassNotFoundException {
        ObjectSource<?> objectSource = getObjectSource(source);
        ObjectChannel channel = objectSource.getChannel(shared);
        while (channel.isOpen()) {
            readNextToken(channel);
        }
    }

    private void readNextToken(ObjectChannel channel) throws IOException, ClassNotFoundException {
        Object token = channel.read();
        Object result = computeFromResults(applyMap(token)
                .collect(Collectors.toList()));
        channel.write(result);
        channel.flush();
    }

    private Stream<Object> applyMap(Object token) {
        return handlerMap.keySet()
                .stream()
                .filter(predicate -> predicate.test(token))
                .map(handlerMap::get)
                .map(objectObjectFunction -> objectObjectFunction.apply(token));
    }

    protected ObjectSource<?> getObjectSource(S source) throws IOException {
        if (source instanceof ObjectSource) {
            return (ObjectSource<?>) source;
        } else {
            return new ObjectSource<>(source);
        }
    }
}
