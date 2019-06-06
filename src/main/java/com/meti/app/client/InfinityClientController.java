package com.meti.app.client;

import com.meti.app.Controls;
import com.meti.app.InfinityController;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.handle.ResponseProcessor;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public class InfinityClientController extends InfinityController {
    protected final Client client;
    protected final ResponseProcessor processor;

    public InfinityClientController(Controls controls) {
        super(controls);
        this.client = toolkit.getClient();
        this.processor = state.singleByClass(ResponseProcessor.class).orElseThrow();
    }
}
