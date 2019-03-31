package com.meti;

import java.util.concurrent.Callable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/30/2019
 */
public abstract class Handler implements Callable<Void> {
    private final ObjectClient client;

    public Handler(ObjectClient client) {
        this.client = client;
    }

    @Override
    public Void call() throws Exception {
        while (!client.isClosed()) {
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

    public abstract Object processToken(Object token) throws Exception;
}
