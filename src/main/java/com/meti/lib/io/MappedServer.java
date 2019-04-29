package com.meti.lib.io;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.meti.lib.util.CollectionUtil.computeFromResults;

public class MappedServer<S extends Source> extends Server<S> {
    private final Map<Predicate<Object>, Function<Object, Object>> map = new HashMap<>();
    private final boolean shared;

    public MappedServer(SourceSupplier<S> supplier, boolean shared) {
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
        return map.keySet()
                .stream()
                .filter(predicate -> predicate.test(token))
                .map(map::get)
                .map(objectObjectFunction -> objectObjectFunction.apply(token));
    }

    private ObjectSource<?> getObjectSource(S source) throws IOException {
        if (source instanceof ObjectSource) {
            return (ObjectSource<?>) source;
        } else {
            return new ObjectSource<>(source);
        }
    }
}
