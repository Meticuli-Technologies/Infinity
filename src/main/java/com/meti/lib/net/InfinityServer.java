package com.meti.lib.net;

import com.meti.app.UserManager;
import com.meti.app.chat.Chat;
import com.meti.lib.event.Event;
import com.meti.lib.event.ServerComponent;

import java.net.ServerSocket;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class InfinityServer extends Server {
    public final Chat chat = new Chat();
    private final UserManager userManager = new UserManager();
    private final Set<ServerComponent<? extends Event, ?>> components = new HashSet<>();

    {
        components.addAll(Arrays.asList(userManager, chat));
    }

    public InfinityServer(ServerSocket serverSocket, ExecutorService service) {
        super(serverSocket, service);
        this.handlerFactory = new InfinityHandlerFactory();
    }

    private class InfinityHandlerFactory implements Function<Client, MappedHandler> {
        @Override
        public MappedHandler apply(Client client) {
            MappedHandler handler = new MappedHandler();
            handler.addAll(getHandlers(client));
            return handler;
        }

        private Set<? extends Handler<Object>> getHandlers(Client client) {
            return components.stream()
                    .flatMap((Function<ServerComponent<? extends Event, ?>, Stream<? extends Handler<Object>>>) component -> component.getHandlers(client))
                    .collect(Collectors.toSet());
        }
    }
}
