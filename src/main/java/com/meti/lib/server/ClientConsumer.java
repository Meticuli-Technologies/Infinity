package com.meti.lib.server;

import com.meti.lib.client.Client;
import com.meti.lib.server.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public class ClientConsumer implements Consumer<Client<?>> {
    private static final Logger logger = LoggerFactory.getLogger(ClientConsumer.class);
    private static final Set<TokenHandler<?>> tokenHandlers = new HashSet<>();
    private final Server server;
    private Consumer<Exception> callback;

    static {
        tokenHandlers.add(new CommandHandler(Command.class));
    }

    public ClientConsumer(Server server) {
        this.server = server;
    }

    @Override
    public void accept(Client<?> client) {
        logger.info("Handling client at " + client.connection.toString());

        while (!client.isClosed()) {
            try {
                Object token = readToken(client);
                boolean hasProcessed = processToken(client, token);

                if(!hasProcessed){
                    client.outputStream.writeObject(new IllegalArgumentException("Token " + token + "has been received but the server has no handlers for it!"));
                }

                client.outputStream.flush();
            } catch (IOException | ClassNotFoundException e) {
                callback.accept(e);
            }
        }
    }

    private boolean processToken(Client client, Object token) throws IOException {
        boolean hasProcessed = false;
        for (TokenHandler<?> tokenHandler : tokenHandlers) {
            if (tokenHandler.tokenClass.isAssignableFrom(token.getClass())) {
                hasProcessed = hasProcessed || processHandler(client, token, tokenHandler);
            }
        }
        return hasProcessed;
    }

    private Object readToken(Client client) throws IOException, ClassNotFoundException {
        Object token = client.inputStream.readObject();
        logger.debug("Received input of " + token.toString());
        return token;
    }

    private boolean processHandler(Client client, Object token, TokenHandler<?> tokenHandler) throws IOException {
        Optional<?> o = tokenHandler.handleObject(token, server);
        if (o.isPresent()) {
            client.outputStream.writeObject(o.get());
            return true;
        }
        return false;
    }

    public void setCallback(Consumer<Exception> callback) {
        this.callback = callback;
    }
}
