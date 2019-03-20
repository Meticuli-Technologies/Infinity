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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class InfinityServer extends Server {
    private final ExecutorService service = Executors.newCachedThreadPool();

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final Map<ClientBuffer, User> userMap = new HashMap<>();

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final Chat chat = new Chat();

    public InfinityServer(int port) throws IOException {
        super(new ServerSocket(port));
    }

    @Override
    public void handleAccept(Socket accept) throws Exception {
        Client client = new Client(accept);
        ClientBuffer buffer = new ClientBuffer(client);

        buffer.handlers.add(new AbstractTokenHandler<>(new TypePredicate<>(Login.class), ((Function<Login, OKResponse>) login -> {
            User user = new User(login.username);
            userMap.put(buffer, user);
            return new OKResponse("Logged in as " + user.name + " successfully.");
        }).compose(new TypeFunction<>(Login.class))));
        buffer.handlers.add(new AbstractTokenHandler<>(new TypePredicate<>(Message.class), ((Function<Message, OKResponse>) message -> {
            chat.add(message);

            return new OKResponse("Message received successfully.");
        }).compose(new TypeFunction<>(Message.class))));

        userMap.put(buffer, null);
        service.submit(buffer);
    }
}
