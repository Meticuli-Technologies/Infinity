package com.meti.lib.net;

import com.meti.lib.State;
import com.meti.lib.net.token.TokenHandler;

import java.io.EOFException;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/3/2019
 */
public class ClientHandler implements Runnable {
    public final Set<TokenHandler<Object>> tokenHandlers = new HashSet<>();
    private final Consumer<Exception> callback;
    private final Client client;
    private final State state;

    public ClientHandler(Consumer<Exception> callback, Client client, State state) {
        this.callback = callback;
        this.client = client;
        this.state = state;
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
        Set<TokenHandler<Object>> processors = tokenHandlers.stream()
                .filter(tokenHandler -> tokenHandler.test(token))
                .peek(this::checkHandlerState)
                .peek(tokenHandler -> tokenHandler.accept(token))
                .collect(Collectors.toSet());

        if (processors.isEmpty()) {
            throw new IllegalArgumentException("Cannot process " + token + ", no valid processors found");
        }
    }

    private void checkHandlerState(TokenHandler<Object> objectTokenHandler) {
        Optional<State> optional = objectTokenHandler.getState();
        if (!optional.isPresent()) {
            objectTokenHandler.setState(state);
        }
    }
}
