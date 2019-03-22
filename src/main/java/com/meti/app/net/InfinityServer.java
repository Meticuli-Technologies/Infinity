package com.meti.app.net;

import com.meti.app.User;
import com.meti.app.feature.Login;
import com.meti.app.feature.Message;
import com.meti.lib.net.AbstractTokenHandler;
import com.meti.lib.net.Client;
import com.meti.lib.net.ClientBuffer;
import com.meti.lib.net.Server;
import com.meti.lib.respond.OKResponse;
import com.meti.lib.util.TypeFunction;
import com.meti.lib.util.TypePredicate;

import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;

public class InfinityServer extends Server {
    private final ExecutorService service = Executors.newCachedThreadPool();

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final Map<ClientBuffer, User> userMap = new HashMap<>();

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final Chat chat = new Chat();

    public InfinityServer(ServerSocket serverSocket) {
        super(serverSocket);

        onConnect = new ClientHandler();
    }

    private class ClientHandler implements Consumer<Client> {
        @Override
        public void accept(Client client) {
            ClientBuffer buffer = new ClientBuffer(client);
            userMap.put(buffer, null);
            service.submit(buffer);

            buffer.handlers.add(new AbstractTokenHandler<>(new TypePredicate<>(Login.class), ((Function<Login, User>) login -> {
                User user = new User(login.username);
                userMap.put(buffer, user);
                return user;
            }).compose(new TypeFunction<>(Login.class))));

            buffer.handlers.add(new AbstractTokenHandler<>(new TypePredicate<>(Message.class), ((Function<Message, OKResponse>) message -> {
                Chat.ChatUpdate update = chat.add(message);
                userMap.keySet().forEach(sender -> sender.update("CHAT", update));

                return new OKResponse("Message received successfully.");
            }).compose(new TypeFunction<>(Message.class))));
        }
    }
}
