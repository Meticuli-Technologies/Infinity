package com.meti.app.control.server;

import com.meti.app.control.util.InfinityController;
import com.meti.app.io.InfinityServer;
import com.meti.lib.util.collect.State;

class InfinityServerController extends InfinityController {
    final InfinityServer server;

    InfinityServerController(State state) {
        super(state);

        server = state.byClassToSingle(InfinityServer.class);
    }
}
