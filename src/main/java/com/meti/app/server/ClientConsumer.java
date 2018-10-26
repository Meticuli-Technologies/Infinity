package com.meti.app.server;

import com.meti.app.server.command.Command;
import com.meti.lib.client.Client;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public class ClientConsumer implements Consumer<Client> {
    private static final Set<TokenHandler<?>> tokenHandlers = new HashSet<>();
    private Consumer<Exception> callback;

    static {
        tokenHandlers.add(new CommandHandler(Command.class));
    }

    @Override
    public void accept(Client client) {
        while (!client.isClosed()) {
            try {
                Object token = client.inputStream.readObject();

                tokenHandlers.stream()
                        .filter(tokenHandler -> tokenHandler.tokenClass.isAssignableFrom(token.getClass()))
                        .map(tokenHandler -> tokenHandler.handleObject(token))
                        .filter(Optional::isPresent).forEach(o -> {
                            try {
                                client.outputStream.writeObject(o);
                            } catch (IOException e) {
                                callback.accept(e);
                            }
                        }
                );

                client.outputStream.flush();
            } catch (IOException | ClassNotFoundException e) {
                callback.accept(e);
            }
        }
    }

    public void setCallback(Consumer<Exception> callback) {
        this.callback = callback;
    }
}
