package com.meti.lib.net;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/21/2019
 */
public class Request implements Serializable {
    public final String key;

    public Request(String key) {
        this.key = key;
    }
}
