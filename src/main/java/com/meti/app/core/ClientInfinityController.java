package com.meti.app.core;

import com.meti.lib.collection.State;
import com.meti.lib.net.query.Querier;

public class ClientInfinityController extends InfinityController {
    protected final InfinityClient client;
    protected final Querier querier;

    public ClientInfinityController(State state) {
        super(state);

        this.client = state.byClass(InfinityClient.class).findAny().orElseThrow();
        this.querier = state.byClass(Querier.class).findAny().orElseThrow();
    }
}
