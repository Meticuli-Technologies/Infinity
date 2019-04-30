package com.meti.app.control.helper;

import com.meti.app.io.InfinityServer;
import com.meti.lib.State;

public class InfinityServerController extends InfinityController {
    protected final InfinityServer server;

    public InfinityServerController(State state) {
        super(state);

        server = this.state.getServer();
    }
}
