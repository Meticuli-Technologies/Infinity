package com.meti.lib.net;

import java.util.concurrent.Callable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/28/2019
 */
public abstract class Processor implements Callable<Void> {
    protected final Client client;

    public Processor(Client client) {
        this.client = client;
    }

    public abstract Object process(Object token);
}
