package com.meti.lib.net;

import com.meti.lib.respond.Respondable;
import com.meti.lib.respond.Response;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/19/2019
 */
public class Query {
    private final Client client;

    public Query(Client client) {
        this.client = client;
    }

    public <R extends Response> R query(Respondable<R> respondable) throws Exception {
        client.write(respondable);
        return client.read(respondable.getResponseClass());
    }

}
