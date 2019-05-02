package com.meti.app.control.client;

import com.meti.app.control.util.InfinityController;
import com.meti.app.io.InfinityClient;
import com.meti.app.io.update.client.MappedUpdater;
import com.meti.lib.io.query.Querier;
import com.meti.lib.util.collect.State;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/29/2019
 */
public class InfinityClientController extends InfinityController {
    protected final InfinityClient client;
    protected final Querier querier;
    protected final MappedUpdater updater;

    protected InfinityClientController(State state) {
        super(state);

        this.client = state.byClassToSingle(InfinityClient.class);
        this.querier = state.byClassToSingle(Querier.class);
        this.updater = state.byClassToSingle(MappedUpdater.class);
    }
}
