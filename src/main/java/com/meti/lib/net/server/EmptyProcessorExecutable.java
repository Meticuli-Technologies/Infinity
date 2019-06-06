package com.meti.lib.net.server;

import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.handle.ResponseProcessor;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
class EmptyProcessorExecutable extends ProcessorExecutable {
    public EmptyProcessorExecutable(Client client, ResponseProcessor processor) {
        super(client, processor);
    }

    @Override
    protected void preClose() {
    }
}
