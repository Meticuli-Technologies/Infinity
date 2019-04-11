package com.meti;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
class Server implements Callable<Void> {
    private final ServerSocket serverSocket;
    private final ExecutorService service;

    public final List<Client> clients = new ArrayList<>();
    private final Chat chat = new Chat();
    public Consumer<Client> onAccept;

    public Server(ServerSocket serverSocket, ExecutorService service) {
        this.serverSocket = serverSocket;
        this.service = service;


        //TODO: add handlers
    }

    @Override
    public Void call() throws Exception {
        while (!serverSocket.isClosed()) {
            Client client = new Client(serverSocket.accept());
            clients.add(client);

            if (onAccept != null) {
                onAccept.accept(client);
            }
            TokenHandler handler = new TokenHandler(client);

            handler.put(new TypePredicate<>(Message.class), ((Function<Message, OKResponse>) message -> {
                chat.add(message);
                return new OKResponse("Message received successfully.");
            }).compose(new TypeFunction<>(Message.class)));
            service.submit(handler);
        }
        return null;
    }
}
