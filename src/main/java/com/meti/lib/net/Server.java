package com.meti.lib.net;

import com.meti.lib.event.Component;
import com.meti.lib.net.source.DelegateSourceSupplier;
import com.meti.lib.net.source.Source;
import com.meti.lib.net.source.SourceSupplier;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;

import static com.meti.lib.trys.TryableFactory.DEFAULT_FACTORY;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/30/2019
 */
public abstract class Server<S extends Source<?, ?>, C extends Client<S>> extends Component<ServerEvent> implements Callable<Void>, Closeable {
    public final List<C> clients = new ArrayList<>();
    private final SourceSupplier<S> sourceSupplier;
    private final Function<S, C> clientConverter;

    public Server(SourceSupplier<? extends Source<?, ?>> supplier, Function<Source<?, ?>, S> sourceConverter, Function<S, C> clientConverter) {
        this(new DelegateSourceSupplier<>(supplier, sourceConverter), clientConverter);
    }

    public Server(SourceSupplier<S> sourceSupplier, Function<S, C> clientConverter) {
        this.sourceSupplier = sourceSupplier;
        this.clientConverter = clientConverter;
    }

    @Override
    public Void call() {
        while (!sourceSupplier.isClosed()) {
            C client = clientConverter.apply(sourceSupplier.get());
            clients.add(client);

            eventManager.fireEvent(ServerEvent.ON_REGISTERED, new ServerEvent(new Object[]{this, client}));
            accept(client);
        }

        return null;
    }

    protected abstract void accept(C client);

    @Override
    public void close() throws IOException {
        clients.stream().forEach(DEFAULT_FACTORY.newConsumer(Client::close));
        sourceSupplier.close();
    }

}
