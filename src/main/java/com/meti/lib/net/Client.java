package com.meti.lib.net;

import com.meti.lib.util.Loop;

import java.io.IOException;

public class Client extends LoopHandler {
    public final Input input = new Input();
    private final ObjectConnection connection;

    public Client(ObjectConnection connection) {
        this.connection = connection;
    }

    @Override
    public void close() throws IOException {
        super.close();
        connection.close();
    }

    @Override
    public Loop getLoop() {
        return new Loop() {
            @Override
            public void loop() throws Exception {
                input.accept(connection.read());
            }
        };
    }
}