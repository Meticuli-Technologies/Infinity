package com.meti.app.core;

import com.meti.lib.collection.State;

public class ClientInfinityController extends InfinityController {
    private final InfinityClient client;

    public ClientInfinityController(State state) {
        super(state);

        this.client = state.byClass(InfinityClient.class).findAny().orElseThrow();
    }
}
