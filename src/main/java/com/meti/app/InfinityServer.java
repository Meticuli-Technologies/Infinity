package com.meti.app;

import com.meti.lib.Server;

import java.net.ServerSocket;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/12/2019
 */
public class InfinityServer extends Server<InfinityClient, InfinityServer.InfinityExecutor> {
    public InfinityServer(ServerSocket serverSocket) {
        super(serverSocket, new InfinityExecutor(), InfinityClient.builder);
    }

    @Override
    public void handleClient(InfinityClient client) {
        Objects.requireNonNull(client);
    }

    public static class InfinityExecutor implements Function<Callable<Optional<Exception>>, Future<Optional<Exception>>> {
        private final ExecutorService service = Executors.newCachedThreadPool();

        @Override
        public Future<Optional<Exception>> apply(Callable<Optional<Exception>> optionalCallable) {
            return service.submit(optionalCallable);
        }
    }
}
