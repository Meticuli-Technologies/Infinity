package com.meti.net;

import com.meti.net.object.ObjectClient;

import java.util.concurrent.Callable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/30/2019
 */
public abstract class Processor implements Callable<Void> {
    private final ObjectClient client;

    public Processor(ObjectClient client) {
        this.client = client;
    }

    @Override
    public Void call() throws Exception {
        while (client.isOpen()) {
            Object result;
            try {
                result = processToken(client.readObject());

                if (result == null) {
                    result = new NullPointerException("No result found.");
                }
            } catch (Exception e) {
                result = e;
            }

            client.writeObject(result);
            client.flush();
        }
        return null;
    }

    public abstract Object processToken(Object token);
}
