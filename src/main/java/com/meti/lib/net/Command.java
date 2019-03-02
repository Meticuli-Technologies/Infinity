package com.meti.lib.net;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/28/2019
 */
public class Command implements Serializable {
    private final String[] args;

    public Command(String... args) {
        this.args = args;
    }
}
