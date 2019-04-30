package com.meti.app.control;

import com.meti.lib.State;
import com.meti.lib.io.ObjectChannel;
import com.meti.lib.io.ObjectSource;
import com.meti.lib.io.Querier;
import com.meti.lib.io.SocketSource;

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
