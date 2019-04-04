package com.meti.app.core;

import com.meti.lib.collection.State;

public class ServerInfinityController extends InfinityController {
    protected final InfinityServer server;

    public ServerInfinityController(State state) {
        super(state);

        this.server = state.byClass(InfinityServer.class).findAny().orElseThrow();
    }
}
