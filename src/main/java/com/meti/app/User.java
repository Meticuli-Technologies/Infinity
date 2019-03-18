package com.meti.app;

import com.meti.lib.Client;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/18/2019
 */
public class User {
    private final String name;
    private final Client client;

    public User(String name, Client client) {
        this.name = name;
        this.client = client;
    }
}
