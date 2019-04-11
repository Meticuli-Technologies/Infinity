package com.meti;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class InfinityClientController extends InfinityController{
    protected final Querier querier;

    public InfinityClientController(State state) {
        super(state);

        this.querier = state.byClassToSingle(Querier.class);
    }
}
