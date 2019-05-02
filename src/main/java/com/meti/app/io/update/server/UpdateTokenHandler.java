package com.meti.app.io.update.server;

import com.meti.app.io.update.UpdateBundle;
import com.meti.app.io.update.client.UpdateRequest;
import com.meti.lib.io.server.handle.TypeTokenHandler;
import com.meti.lib.io.source.SocketSource;

public class UpdateTokenHandler extends TypeTokenHandler<UpdateRequest, SocketSource, UpdateBundle> {
    private UpdateManager<SocketSource> updateManager;

    public UpdateTokenHandler(UpdateManager<SocketSource> updateManager) {
        super(UpdateRequest.class);
        this.updateManager = updateManager;
    }

    @Override
    protected UpdateBundle handle(UpdateRequest updateRequest, SocketSource source) {
        return new UpdateBundle(updateManager.getUpdates(source));
    }
}