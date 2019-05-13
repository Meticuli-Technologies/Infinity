package com.meti;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/7/2019
 */
interface TokenHandler {
    boolean canHandle(Object token);

    void handle(Object token);
}
