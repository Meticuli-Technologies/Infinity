package com.meti.app.control.helper;

import com.meti.lib.State;
import com.meti.lib.io.channel.ObjectChannel;
import com.meti.lib.io.source.ObjectSource;
import com.meti.lib.io.Querier;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/29/2019
 */
public class InfinityClientController extends InfinityController{
   private final ObjectSource<?> client;
   private final ObjectChannel channel;
   private final Querier querier;

    public InfinityClientController(State state) {
        super(state);

        this.client = this.state.getClient();
        this.channel = this.state.getChannel();
        this.querier = this.state.getQuerier();
    }
}
