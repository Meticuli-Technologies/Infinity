package com.meti.app.io;

import com.meti.lib.io.query.Querier;
import com.meti.lib.io.source.ObjectSource;
import com.meti.lib.io.source.SocketSource;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/30/2019
 */
public class InfinityClient extends ObjectSource<SocketSource> {
    private Querier querier;

    public InfinityClient(SocketSource source) throws IOException {
        super(source);
    }

    public Querier getQuerier(boolean shared) {
        if (querier == null) {
            querier = new Querier(getChannel(shared));
        }
        return querier;
    }
}