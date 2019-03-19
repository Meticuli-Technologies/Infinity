package com.meti.app.net;

import com.meti.app.User;
import com.meti.app.feature.Login;
import com.meti.app.feature.LoginHandler;
import com.meti.app.feature.Message;
import com.meti.app.feature.MessageHandler;
import com.meti.lib.net.Client;
import com.meti.lib.net.ClientHandler;
import com.meti.lib.net.Server;
import com.meti.lib.respond.CachedResponse;
import com.meti.lib.util.TypePredicate;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InfinityServer extends Server {
    private final ExecutorService service = Executors.newCachedThreadPool();
    private final List<User> users = new ArrayList<>();

    public InfinityServer(int port) throws IOException {
        super(new ServerSocket(port));
    }

    @Override
    public void handleAccept(Socket accept) throws Exception {
        service.submit(new InstanceHandler(new Client(accept)));
    }

    private class InstanceHandler extends ClientHandler implements Callable<Void> {
        private final Map<Predicate<Object>, Function<Object, ? extends Serializable>> map = new HashMap<>();
        private User user;

        {
            map.put(new TypePredicate<>(Login.class), new LoginHandler(client, newUser -> {
                user = newUser;
                users.add(newUser);
            }));
            map.put(new TypePredicate<>(Message.class), new MessageHandler(new MessageConsumer()));
        }

        InstanceHandler(Client client) {
            super(client);
        }

        @Override
        public Void call() throws IOException {
            while (!Thread.interrupted() || !client.getSocket().isClosed()) {
                try {
                    Object token = client.read();
                    client.write(processToken(token));
                } catch (Exception e) {
                    client.write(new CachedResponse<>(e));
                }
            }
            return null;
        }

        private Set<Serializable> processToken(Object token) {
            Set<Serializable> set = map.keySet().stream()
                    .filter(objectPredicate -> objectPredicate.test(token))
                    .map(map::get)
                    .map(objectSerializableFunction -> objectSerializableFunction.apply(token))
                    .collect(Collectors.toSet());

            if (set.isEmpty()) {
                throw new IllegalStateException("Invalid token: " + token);
            }

            return set;
        }

        private class MessageConsumer implements Consumer<Message> {
            @Override
            public void accept(Message message) {
                for (User nextUser : users) {
                    try {
                        nextUser.client.write(new Message.MessageUpdate(user, message));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
