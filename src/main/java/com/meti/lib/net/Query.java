package com.meti.lib.net;

import com.meti.lib.respond.Respondable;
import com.meti.lib.respond.Response;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/19/2019
 */
public class Query {
    private final Client client;

    public Query(Client client) {
        this.client = client;
    }

    public <R extends Response> R query(Respondable<R> respondable) throws Exception {
        client.write(respondable);
        return client.read(respondable.getResponseClass());
    }

    public <R extends Response> R query(Respondable<R> respondable, Duration timeout) throws IOException, ClassNotFoundException, InterruptedException, ExecutionException, TimeoutException {
        client.write(respondable);

        return new FutureTask<>((Callable<R>) () -> client.read(respondable.getResponseClass()))
                .get(timeout.toMillis(), TimeUnit.MILLISECONDS);
    }
}
