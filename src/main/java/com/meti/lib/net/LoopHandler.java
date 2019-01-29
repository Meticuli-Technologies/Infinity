package com.meti.lib.net;

import com.meti.lib.util.Loop;

import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public abstract class LoopHandler implements Closeable {
    private final Loop loop = getLoop();
    private Future<Optional<Exception>> loopSubmission;

    public Future<Optional<Exception>> start(ExecutorService service) {
        return loopSubmission = service.submit(loop);
    }

    @Override
    public void close() throws IOException {
        if (!loopSubmission.isCancelled()) {
            stop();
        }
    }

    public void stop() {
        loopSubmission.cancel(true);
    }

    public abstract Loop getLoop();
}
