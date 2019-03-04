package com.meti.app;

import com.meti.lib.State;
import com.meti.lib.net.Client;
import com.meti.lib.net.ClientHandler;
import com.meti.lib.net.Server;
import com.meti.lib.net.token.CommandHandler;

import java.net.ServerSocket;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/3/2019
 */
class InfinityServer extends Server {
    public InfinityServer(ServerSocket serverSocket, State state) {
        super(serverSocket, state);
    }

    @Override
    protected ClientHandler createHandler(Consumer<Exception> callback, Client client, State state) {
        ClientHandler clientHandler = new ClientHandler(callback, client, state);
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.implementations.add(new Chat.Add());

        clientHandler.tokenHandlers.add(commandHandler);
        return clientHandler;
    }
}
