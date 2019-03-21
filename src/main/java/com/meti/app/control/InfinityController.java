package com.meti.app.control;

import com.meti.lib.State;
import com.meti.lib.fx.Controller;
import com.meti.lib.net.Server;

import java.util.Optional;
import java.util.concurrent.ExecutorService;

public class InfinityController extends Controller {
    protected final ExecutorService service;

    public InfinityController(State state) {
        super(state);

        this.service = state.byClassToSingle(ExecutorService.class).orElseThrow();
    }

    public Optional<Server> getServer() {
        return state.byClassToSingle(Server.class);
    }
}
