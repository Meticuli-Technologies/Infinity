package com.meti;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public interface TokenHandler {
    boolean canHandle(Object token);

    void handle(Object token, Client client);
}
