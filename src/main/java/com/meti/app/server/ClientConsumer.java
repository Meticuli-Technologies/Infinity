package com.meti.app.server;

import com.meti.app.server.command.Command;
import com.meti.lib.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public class ClientConsumer implements Consumer<Client> {
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
    public void accept(Client client) {
        logger.info("Handling client at " + client.socket.getInetAddress());

        while (!client.isClosed()) {
            try {
                Object token = client.inputStream.readObject();
                logger.debug("Received input of " + token.toString());

                boolean hasProcessed = false;
                for (TokenHandler<?> tokenHandler : tokenHandlers) {
                    if (tokenHandler.tokenClass.isAssignableFrom(token.getClass())) {
                        Optional<?> o = tokenHandler.handleObject(token, server);
                        if (o.isPresent()) {
                            try {
                                client.outputStream.writeObject(o.get());
                                hasProcessed = true;
                            } catch (IOException e) {
                                callback.accept(e);
                            }
                        }
                    }
                }

                if(!hasProcessed){
                    client.outputStream.writeObject(new IllegalArgumentException("Token " + token + "has been received but the server has no handlers for it!"));
                }

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
