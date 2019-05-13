package com.meti;

import javafx.scene.Parent;

import java.io.IOException;
import java.util.logging.Logger;

public abstract class ClientViewModel {
    protected final Logger logger;
    protected final Client client;
    protected final InfinityClientTokenHandler handler;

    public ClientViewModel(Logger logger, Client client, InfinityClientTokenHandler handler) {
        this.logger = logger;
        this.client = client;
        this.handler = handler;
    }

    public abstract String getName();
    public abstract Parent getRoot() throws IOException;
}
