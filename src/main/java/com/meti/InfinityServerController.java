package com.meti;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class InfinityServerController extends InfinityController{
    protected final Server server;

    public InfinityServerController(State state) {
        super(state);

        this.server = state.byClassToSingle(Server.class);
    }
}
