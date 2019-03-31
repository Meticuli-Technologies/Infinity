package com.meti.app.core;

import com.meti.lib.collection.State;
import com.meti.lib.log.Console;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/31/2019
 */
public class InfinityState extends State {
    public Console getConsole() {
        return byClass(Console.class).findAny().orElseThrow();
    }
}
