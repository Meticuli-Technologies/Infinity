package com.meti.lib.concurrent;

import java.time.Duration;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
interface Terminator {
    void terminate(Duration duration) throws InterruptedException;
}
