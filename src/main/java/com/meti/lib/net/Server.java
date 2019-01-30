package com.meti.lib.net;

import com.meti.lib.util.Loop;

import java.util.function.Consumer;

public abstract class Server extends LoopHandler {
    private final Consumer<Client> clientConsumer;

    protected Server(Consumer<Client> clientConsumer) {
        this.clientConsumer = clientConsumer;
    }

    @Override
    public Loop getLoop() {
        return new Loop() {
            @Override
            public void loop() throws Exception {
                clientConsumer.accept(accept());
            }
        };
    }

    public abstract Client accept() throws Exception;
}
