package com.meti.app.io.update.server;

import com.meti.app.io.update.UpdateBundle;
import com.meti.app.io.update.client.UpdateRequest;
import com.meti.lib.io.server.handle.TypeTokenHandler;
import com.meti.lib.io.source.Source;

public class UpdateTokenHandler extends TypeTokenHandler<UpdateRequest, UpdateBundle> {
    private UpdateManager<Source> updateManager;

    public UpdateTokenHandler(UpdateManager<Source> updateManager) {
        super(UpdateRequest.class);
        this.updateManager = updateManager;
    }

    @Override
    protected UpdateBundle handle(UpdateRequest request, Source source) {
        return new UpdateBundle(updateManager.getUpdates(source));
    }
}