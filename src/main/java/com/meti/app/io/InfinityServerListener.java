package com.meti.app.io;

import com.meti.lib.net.Processor;
import com.meti.lib.net.Server;
import com.meti.lib.net.ServerListener;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/26/2019
 */
public class InfinityServerListener extends ServerListener {
    private final ExecutorService service;

    public InfinityServerListener(int port, ExecutorService service) throws IOException {
        super(new Server(port));
        this.service = service;
        this.service.submit(this);
    }

    public void listen(){
        listen(service::submit);
    }

    @Override
    protected void process(Processor processor) {
        service.submit(processor);
    }
}
