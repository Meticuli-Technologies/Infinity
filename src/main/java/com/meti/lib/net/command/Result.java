package com.meti.lib.net.command;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/18/2018
 */
public class Result<R extends Serializable> implements Serializable {
    public final R value;

    public Result(R value) {
        this.value = value;
    }
}
