package com.meti.lib.net;

import com.meti.lib.State;
import com.meti.lib.handle.Handler;
import com.meti.lib.handle.HandlerMap;

import java.io.EOFException;
import java.net.SocketException;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/3/2019
 */
public class ClientHandler implements Runnable {
    public final HandlerMap<Object, Handler<Object>> handlers = new HandlerMap<>();
    private final Consumer<Exception> callback;
    private final Client client;
    private final State state;

    public ClientHandler(Consumer<Exception> callback, Client client, State state) {
        this.callback = callback;
        this.client = client;
        this.state = state;
    }

    @Override
    public void run() {
        handlers.setState(state);

        while (!Thread.interrupted() && !client.socket.isClosed()) {
            try {
                Object read = client.read();
                handlers.accept(read);
            } catch (Exception e) {
                if (e instanceof SocketException || e instanceof EOFException) {
                    break;
                } else {
                    callback.accept(e);
                }
            }
        }
    }
}
