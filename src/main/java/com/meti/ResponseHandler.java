package com.meti;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public interface ResponseHandler {
    boolean canHandle(Object response);

    void handle(Object response, Client client);
}
