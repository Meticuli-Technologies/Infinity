package com.meti.app.control.client;

import com.meti.app.control.util.InfinityController;
import com.meti.app.io.InfinityClient;
import com.meti.lib.State;
import com.meti.lib.io.Querier;
import com.meti.lib.io.channel.ObjectChannel;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/29/2019
 */
public class InfinityClientController extends InfinityController {
   protected final InfinityClient client;
   private final ObjectChannel channel;
   private final Querier querier;

    public InfinityClientController(State state) {
        super(state);

        this.client = this.state.getClient();
        this.channel = this.state.getChannel();
        this.querier = this.state.getQuerier();
    }
}
