package com.meti.lib.net;

import com.meti.app.UserManager;
import com.meti.app.chat.Chat;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class InfinityServer extends Server {
    private final UserManager userManager = new UserManager();
    public final Chat chat = new Chat();

    public InfinityServer(ServerSocket serverSocket, ExecutorService service) {
        super(serverSocket, service);
        this.handlerFactory = new InfinityHandlerFactory();
    }

    private class InfinityHandlerFactory implements Function<Client, MappedHandler> {
        @Override
        public MappedHandler apply(Client client) {
            MappedHandler handler = new MappedHandler();
            handler.add(userManager.getLoginHandler(client));
            handler.add(chat.getRegisteredMessageHandler());
            handler.add(chat.getRequestHandler());
            return handler;
        }
    }
}
