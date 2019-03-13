package com.meti.app;

import com.meti.lib.Client;
import com.meti.lib.Handler;
import com.meti.lib.HandlerMap;

import java.io.IOException;
import java.net.Socket;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/12/2019
 */
public class InfinityClient extends Client  {
    public static Function<Socket, InfinityClient> builder = socket -> {
        try {
            return new InfinityClient(socket);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    };
    public final HandlerMap<Object> handlers = new HandlerMap<>();

    public InfinityClient(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    protected void handleObject(Object token) {
        if(handlers.isEmpty()){
            throw new IllegalStateException("No handlers were found");
        }

        Stream<Handler<Object>> stream = handlers.process(token);
        if (stream.count() == 0) {
            throw new IllegalArgumentException("No handlers for " + token);
        }
    }
}
