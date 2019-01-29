package com.meti.lib.net;

import com.meti.lib.util.Loop;

import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Client implements Closeable {
    public final Input input = new Input();
    private final ObjectConnection connection;
    private Loop loop = new ClientLoop();
    private Future<Optional<Exception>> loopSubmission;

    public Client(ObjectConnection connection) {
        this.connection = connection;
    }

    public Future<Optional<Exception>> start(ExecutorService service) {
        return loopSubmission = service.submit(loop);
    }

    @Override
    public void close() throws IOException {
        if (!loopSubmission.isCancelled()) {
            stop();
        }

        connection.close();
    }

    public void stop() {
        loopSubmission.cancel(true);
    }

    private class ClientLoop extends Loop {
        @Override
        public void loop() throws Exception {
            input.accept(connection.read());
        }
    }
}