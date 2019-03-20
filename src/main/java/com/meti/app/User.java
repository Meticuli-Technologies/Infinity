package com.meti.app;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/18/2019
 */
public class User implements Serializable {
    public final String name;

    public User(String name) {
        this.name = name;
    }
}
