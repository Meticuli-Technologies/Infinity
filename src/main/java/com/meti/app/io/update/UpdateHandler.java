package com.meti.app.io.update;

import com.meti.app.io.update.UpdateBundle;
import com.meti.app.io.update.UpdateRequest;
import com.meti.lib.io.server.handle.TypeHandler;
import com.meti.lib.io.source.SocketSource;

public class UpdateHandler extends TypeHandler<UpdateRequest, SocketSource, UpdateBundle> {
    public UpdateHandler() {
        super(UpdateRequest.class);
    }

    @Override
    protected UpdateBundle handle(UpdateRequest updateRequest, SocketSource source) {
        return null;
    }
}
