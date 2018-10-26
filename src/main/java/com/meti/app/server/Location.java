package com.meti.app.server;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/26/2018
 */
public class Location implements Serializable {
    public final String value;

    public Location(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
