package com.meti.app;

import com.meti.lib.State;
import com.meti.lib.net.Client;
import com.meti.lib.net.ClientHandler;
import com.meti.lib.net.Command;
import com.meti.lib.net.Server;
import com.meti.lib.net.token.TokenHandler;

import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/3/2019
 */
class InfinityServer extends Server {
    private final State state = new State();

    public InfinityServer(ServerSocket serverSocket) {
        super(serverSocket);
    }

    @Override
    protected ClientHandler createHandler(Consumer<Exception> callback, Client client) {
        ClientHandler handler = new ClientHandler(callback, client);
        handler.handlers.add(new CommandHandler());
        return handler;
    }

    private class CommandHandler extends TokenHandler<Command> {
        public CommandHandler() {
            super(Command.class);
        }

        @Override
        public void accept(Command command) {
        }
    }
}
