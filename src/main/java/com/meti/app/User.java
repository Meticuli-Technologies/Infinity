package com.meti.app;

import com.meti.lib.net.Client;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/18/2019
 */
public class User implements Serializable {
    public final String name;
    public transient final Client client;

    public User(String name, Client client) {
        this.name = name;
        this.client = client;
    }
}
