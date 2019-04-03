package com.meti.app.core;

import com.meti.app.control.Message;
import com.meti.app.server.Chat;
import com.meti.lib.collection.TypeFunction;
import com.meti.lib.collection.TypePredicate;
import com.meti.lib.net.MappedProcessor;
import com.meti.lib.net.Server;
import com.meti.lib.net.handle.AbstractTokenHandler;
import com.meti.lib.net.object.ObjectClient;
import com.meti.lib.net.object.ObjectSource;
import com.meti.lib.net.query.OKResponse;
import com.meti.lib.net.source.SocketSourceSupplier;
import com.meti.lib.trys.Catcher;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.meti.lib.trys.TryableFactory.DEFAULT_FACTORY;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/31/2019
 */
public class InfinityServer extends Server<ObjectSource, ObjectClient> {
    private final Consumer<Future<?>> futureConsumer;
    private final ExecutorService service;

    private final Chat chat = new Chat();

    public InfinityServer(int port, Catcher catcher, Consumer<Future<?>> futureConsumer, ExecutorService service) throws IOException {
        super(new SocketSourceSupplier(port, catcher),
                DEFAULT_FACTORY.newFunction(ObjectSource::new),
                ObjectClient::new
        );
        this.futureConsumer = futureConsumer;
        this.service = service;
    }

    @Override
    protected void accept(ObjectClient client) {
        futureConsumer.accept(service.submit(new InfinityProcessor(client)));
    }

    private class InfinityProcessor extends MappedProcessor {
        InfinityProcessor(ObjectClient client) {
            super(client);

            tokenHandlers.add(new AbstractTokenHandler<>(
                    new TypePredicate<>(Message.class),
                    ((Function<Message, Object>) message -> {
                        chat.add(message);
                        return new OKResponse("Received message \"" + message.value + "\" successfully.");
                    }).compose(new TypeFunction<>(Message.class))
            ));

     /*       tokenHandlers.add(new AbstractTokenHandler<>());*/
        }
    }
}
