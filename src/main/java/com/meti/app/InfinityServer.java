package com.meti.app;

import com.meti.lib.State;
import com.meti.lib.net.Client;
import com.meti.lib.net.ClientHandler;
import com.meti.lib.net.Server;

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
    public State buildState(State state) {
        state.add(new Chat());
        return state;
    }

    @Override
    protected ClientHandler createHandler(Consumer<Exception> callback, Client client, State state) {
        ClientHandler clientHandler = new ClientHandler(callback, client, state);
  /*      CommandHandler commandHandler = new CommandHandler();
        commandHandler.implementations.add(new Chat.Add());

        clientHandler.tokenHandlers.add(commandHandler);*/
        return clientHandler;
    }
}
