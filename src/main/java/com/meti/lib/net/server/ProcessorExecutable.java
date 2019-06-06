package com.meti.lib.net.server;

import com.meti.lib.concurrent.LoopedExecutable;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.handle.ResponseProcessor;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public abstract class ProcessorExecutable extends LoopedExecutable {
    private final Client client;
    private final ResponseProcessor processor;

    public ProcessorExecutable(Client client, ResponseProcessor processor) {
        this.client = client;
        this.processor = processor;
    }

    @Override
    protected void loop() {
        if (client.isClosed()) {
            stop();
        }
        processNextResponseOrThrow();
    }

    @Override
    public void close() throws IOException {
        super.close();
        client.close();
    }

    private void processNextResponseOrThrow() {
        try {
            processor.processNextResponse();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
