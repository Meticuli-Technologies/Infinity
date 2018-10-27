package com.meti.lib;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/27/2018
 */
public interface Requestable<P, R> {
    R request(P parameter);
}
