package com.meti.app;

import com.meti.lib.net.Client;
import com.meti.lib.net.ClientHandler;
import com.meti.lib.net.Command;
import com.meti.lib.net.Server;

import java.net.ServerSocket;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/3/2019
 */
class InfinityServer extends Server {
    public InfinityServer(ServerSocket serverSocket) {
        super(serverSocket);
    }

    @Override
    protected ClientHandler createHandler(Consumer<Exception> callback, Client client) {
        ClientHandler handler = new ClientHandler(callback, client);
        handler.handlers.add(new CommandHandler());
        return handler;
    }

    private class CommandHandler extends com.meti.lib.net.token.TokenHandler<Command> {
        public CommandHandler() {
            super(Command.class);
        }

        @Override
        public void accept(Command command) {
            System.out.println(command);
        }
    }
}
