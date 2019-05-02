package com.meti.lib.io.server;

import com.meti.lib.io.channel.ObjectChannel;
import com.meti.lib.io.server.handle.TokenHandler;
import com.meti.lib.io.source.ObjectSource;
import com.meti.lib.io.source.Source;
import com.meti.lib.io.source.supplier.SourceSupplier;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.meti.lib.util.collect.Collections.computeFromResults;

public class MappedServer<S extends Source, T extends SourceSupplier<S>> extends Server<S, T> {
    protected final Set<TokenHandler<Object, ?>> tokenHandlers = new HashSet<>();
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
            readNextToken(channel, source);
        }
    }

    private void readNextToken(ObjectChannel channel, S source) throws IOException, ClassNotFoundException {
        Object token = channel.read();
        Object result = computeFromResults(applyMap(token, source)
                .collect(Collectors.toList()));
        channel.write(result);
        channel.flush();
    }

    private Stream<Object> applyMap(Object token, S source) {
        return tokenHandlers.stream()
                .filter(tokenHandler -> tokenHandler.test(token))
                .map(tokenHandler -> tokenHandler.apply(token, source));
    }

    protected ObjectSource<?> getObjectSource(S source) throws IOException {
        if (source instanceof ObjectSource) {
            return (ObjectSource<?>) source;
        } else {
            return new ObjectSource<>(source);
        }
    }
}
