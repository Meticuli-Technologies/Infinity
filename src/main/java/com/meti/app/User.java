package com.meti.app;

import com.meti.lib.net.Response;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class User implements Response {
    public static final User UNKNOWN = new User("UNKNOWN");
    private final String username;

    public User(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return username;
    }
}
