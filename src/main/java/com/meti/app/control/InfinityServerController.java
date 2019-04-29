package com.meti.app.control;

import com.meti.app.io.InfinityServer;
import com.meti.lib.State;

public class InfinityServerController extends InfinityController {
    final InfinityServer server;

    public InfinityServerController(State state) {
        super(state);

        server = this.state.getServer();
    }
}
