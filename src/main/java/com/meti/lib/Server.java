package com.meti.lib;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class Server<C extends Client, F extends Function<Callable<Optional<Exception>>, Future<Optional<Exception>>>> implements Callable<Optional<Exception>>, Closeable {
    public final Set<C> clients = new HashSet<>();
    public final ServerSocket serverSocket;

    private final Function<Socket, C> clientBuilder;
    private final F function;

    public Server(ServerSocket serverSocket, F function, Function<Socket, C> clientBuilder) {
        this.function = function;
        this.clientBuilder = clientBuilder;
        this.serverSocket = serverSocket;
    }

    @Override
    public Optional<Exception> call() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                C client = clientBuilder.apply(socket);
                clients.add(client);
                handleClient(client);
            }

            return Optional.empty();
        } catch (IOException e) {
            return Optional.of(e);
        }
    }

    public abstract void handleClient(C client);

    @Override
    public void close() throws IOException {
        serverSocket.close();

        Set<C> opened = clients.stream()
                .peek(c -> {
                    try {
                        c.socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
                .filter(c -> !c.socket.isClosed())
                .collect(Collectors.toSet());

        if (opened.size() != 0) {
            StringBuilder builder = new StringBuilder("Clients are still open:");
            builder.append(opened.stream()
                    .map(c -> c.socket)
                    .map(Socket::getInetAddress)
                    .map(InetAddress::toString)
                    .collect(Collectors.joining("\t\n")));

            throw new IllegalStateException(builder.toString());
        }
    }

    public Future<Optional<Exception>> listen() {
        return function.apply(this);
    }
}