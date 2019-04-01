package com.meti.app.core;

import com.meti.lib.collection.State;
import com.meti.lib.fx.Controller;
import com.meti.lib.log.Console;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/31/2019
 */
public class InfinityController extends Controller {
    protected final ExecutorService service;
    protected final Console console;

    public InfinityController(State state) {
        super(state);

        this.service = state.byClass(ExecutorService.class)
                .findAny()
                .orElseThrow();

        this.console = state.byClass(Console.class)
                .findAny()
                .orElseThrow();
    }
}
