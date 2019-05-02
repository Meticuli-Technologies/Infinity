package com.meti.app.io.update;

import com.meti.lib.io.server.handle.TypeHandler;
import com.meti.lib.io.source.SocketSource;

public class UpdateHandler extends TypeHandler<UpdateRequest, SocketSource, UpdateBundle> {
    private UpdateManager<SocketSource> updateManager;

    public UpdateHandler(UpdateManager<SocketSource> updateManager) {
        super(UpdateRequest.class);
        this.updateManager = updateManager;
    }

    @Override
    protected UpdateBundle handle(UpdateRequest updateRequest, SocketSource source) {
        return new UpdateBundle(updateManager.getUpdates(source));
    }
}