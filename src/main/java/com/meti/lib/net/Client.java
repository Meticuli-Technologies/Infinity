package com.meti.lib.net;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class Client<T extends SocketConnection> {
    public final T connection;

    public Client(T connection) {
        this.connection = connection;
    }
}
