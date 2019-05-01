package com.meti.app.control.client;

import com.meti.app.control.util.InfinityController;
import com.meti.app.io.InfinityClient;
import com.meti.lib.util.collect.State;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/29/2019
 */
class InfinityClientController extends InfinityController {
   final InfinityClient client;

    InfinityClientController(State state) {
        super(state);

        this.client = state.byClassToSingle(InfinityClient.class);
    }
}
