package com.meti.app.control;

import com.meti.lib.control.Controller;
import com.meti.lib.log.Console;
import com.meti.lib.util.State;
import javafx.application.HostServices;

import java.util.concurrent.ExecutorService;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class InfinityController extends Controller {
    protected final ExecutorService service;
    protected final Console console;
    protected final HostServices hostServices;

    public InfinityController(State state) {
        super(state);

        this.hostServices = state.byClassToSingle(HostServices.class).orElseThrow();
        this.service = state.byClassToSingle(ExecutorService.class).orElseThrow();
        this.console = state.byClassToSingle(Console.class).orElseThrow();
    }
}
