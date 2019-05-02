package com.meti.app.io.update.client;

import com.meti.app.io.update.Update;
import com.meti.app.io.update.UpdateBundle;
import com.meti.lib.io.query.Querier;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public abstract class Updater {
    private final Querier querier;

    protected Updater(Querier querier) {
        this.querier = querier;
    }

    public UpdateBundle getUpdates() throws IOException, ExecutionException, InterruptedException {
        return querier.query(new UpdateRequest()).get();
    }

    public abstract void handleUpdate(Update update);
}
