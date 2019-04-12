package com.meti.app.control;

import com.meti.app.User;
import com.meti.lib.net.Client;
import com.meti.lib.net.Querier;
import com.meti.lib.util.State;

import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class InfinityClientController extends InfinityController{
    protected final Querier querier;
    protected final Client client;
    protected final User user;

    public InfinityClientController(State state) {
        super(state);

        this.querier = state.byClassToSingle(Querier.class).orElseThrow();
        this.client = state.byClassToSingle(Client.class).orElseThrow();
        this.user = state.byClassToSingle(User.class).orElse(User.UNKNOWN);
    }
}
