package com.meti.app.io.update.client;

import com.meti.app.io.Request;
import com.meti.app.io.update.UpdateBundle;

public class UpdateRequest implements Request<UpdateBundle> {
    @Override
    public Class<UpdateBundle> getTypeClass() {
        return UpdateBundle.class;
    }
}
