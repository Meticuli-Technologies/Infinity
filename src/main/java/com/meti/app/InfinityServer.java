package com.meti.app;

import com.meti.lib.*;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class InfinityServer extends Server {
    private final ExecutorService service = Executors.newCachedThreadPool();
    private final List<User> users = new ArrayList<>();

    public InfinityServer(int port) throws IOException {
        super(new ServerSocket(port));
    }

    @Override
    public void handleAccept(Socket accept) throws Exception {
        System.out.println("Located client at " + accept.getInetAddress());
        service.submit(new ClientHandler(new Client(accept)));
    }

    private class ClientHandler implements Callable<Void> {
        private final Map<Predicate<Object>, Function<Object, ? extends Serializable>> map = new HashMap<>();
        private final List<Message> messages = new ArrayList<>();
        private Client client;

        {
            map.put(new TypePredicate<>(Login.class), new Function<Object, Login.LoginResponse>() {
                @Override
                public Login.LoginResponse apply(Object o) {
                    Login login = (Login) o;
                    users.add(new User(login.username, client));
                    return new Login.LoginResponse("Successfully logged in with username: " + login.username);
                }
            });

            map.put(new TypePredicate<>(Message.class), new Function<Object, OKResponse>() {
                @Override
                public OKResponse apply(Object o) {
                    Message message = (Message) o;
                    messages.add(message);
                    return new OKResponse();
                }
            });
        }

        public ClientHandler(Client client) {
            this.client = client;
        }

        @Override
        public Void call() throws IOException {
            System.out.println("Handling client at " + client.getSocket().getInetAddress());

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

    }
}
