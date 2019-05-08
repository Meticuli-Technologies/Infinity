package com.meti;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/7/2019
 */
public class Client implements Closeable {
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    public Client(InetAddress address, int port) throws IOException {
        this.socket = new Socket(address, port);
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }

    public void flush() throws IOException {
        outputStream.flush();
    }

    public Querier listen(Consumer<Querier> executor) {
        Querier querier = new Querier();
        executor.accept(querier);
        return querier;
    }

    public Object read() throws IOException, ClassNotFoundException {
        return inputStream.readUnshared();
    }

    public void write(Object token) throws IOException {
        outputStream.writeUnshared(token);
    }

    private class Querier implements Callable<Querier> {
        private final ArrayBlockingQueue<CompletableFuture<Object>> futures = new ArrayBlockingQueue<>(16);

        @Override
        public Querier call() throws Exception {
            CompletableFuture<Object> future = futures.take();
            Object token = inputStream.readUnshared();
            if (token instanceof Throwable) {
                future.completeExceptionally((Throwable) token);
            } else {
                future.complete(token);
            }
            return this;
        }

        public <T> CompletableFuture<T> query(Object token, Class<T> expectedClass) throws IOException {
            return query(token).thenApply(expectedClass::cast);
        }

        public CompletableFuture<Object> query(Object token) throws IOException {
            outputStream.writeUnshared(token);
            outputStream.flush();

            CompletableFuture<Object> future = new CompletableFuture<>();
            futures.add(future);
            return future;
        }
    }
}