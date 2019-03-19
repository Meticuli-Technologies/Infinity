package com.meti.lib.net;

import java.util.LinkedList;
import java.util.concurrent.Callable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/19/2019
 */
public class Queuer extends LinkedList<Object> implements Callable<Void> {
    private final Client client;

    public Queuer(Client client) {
        this.client = client;
    }

    @Override
    public Void call() throws Exception {
        while(!client.getSocket().isClosed()){
            add(client.read());
        }
        return null;
    }
}
