package com.meti.lib.net.object;

import com.meti.lib.net.Client;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/30/2019
 */
public class ObjectClient extends Client<ObjectSource> {
    public ObjectClient(ObjectSource source) {
        super(source);
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        return source.getInputStream().readObject();
    }

    public void writeObject(Object object) throws IOException {
        source.getOutputStream().writeObject(object);
    }
}
