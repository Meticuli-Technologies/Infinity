package com.meti.app.net;

import com.meti.app.feature.Message;
import com.meti.lib.net.AbstractTokenHandler;
import com.meti.lib.net.Client;
import com.meti.lib.net.Server;
import com.meti.lib.respond.OKResponse;
import com.meti.lib.util.TypePredicate;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class InfinityServer extends Server {
    private final ExecutorService service = Executors.newCachedThreadPool();
    private final Chat chat = new Chat();

    public InfinityServer(int port) throws IOException {
        super(new ServerSocket(port));
    }

    @Override
    public void handleAccept(Socket accept) throws Exception {
        Client client = new Client(accept);
        client.handlers.add(new AbstractTokenHandler<>(new TypePredicate<>(Message.class), new Function<Object, OKResponse>() {
            @Override
            public OKResponse apply(Object o) {
                chat.add((Message) o);

                //TODO: distribute message to clients
                return new OKResponse();
            }
        }));

        service.submit(client);
    }
}
