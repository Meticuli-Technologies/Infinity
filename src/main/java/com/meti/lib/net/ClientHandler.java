package com.meti.lib.net;

import com.meti.lib.net.token.TokenHandler;

import java.io.EOFException;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/3/2019
 */
public class ClientHandler implements Runnable {
    private final Set<TokenHandler<?>> handlers = new HashSet<>();
    private final Consumer<Exception> callback;
    private final Client client;

    public ClientHandler(Consumer<Exception> callback, Client client) {
        this.callback = callback;
        this.client = client;
    }

    @Override
    public void run() {
        while (!Thread.interrupted() && !client.socket.isClosed()) {
            try {
                Object read = client.read();
                handleToken(read);
            } catch (Exception e) {
                if (e instanceof SocketException || e instanceof EOFException) {
                    break;
                } else {
                    callback.accept(e);
                }
            }
        }
    }

    private void handleToken(Object token) {
        Set<TokenHandler<?>> processors = handlers.stream()
                .filter(tokenHandler -> tokenHandler.tClass.isAssignableFrom(token.getClass()))
                .peek(tokenHandler -> tokenHandler.process(token))
                .collect(Collectors.toSet());

        if (processors.isEmpty()) {
            throw new IllegalArgumentException("Cannot process " + token + ", no valid processors found");
        }
    }
}
