package com.meti.lib.net.object;

import com.meti.lib.net.source.DelegateSource;
import com.meti.lib.net.source.Source;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/30/2019
 */
public class ObjectSource extends DelegateSource<ObjectInputStream, ObjectOutputStream> {
    public ObjectSource(Source<?, ?> parent) throws IOException {
        super(parent);
    }

    @Override
    public ObjectInputStream constructInputStream(Source<?, ?> source) throws IOException {
        return new ObjectInputStream(source.getInputStream());
    }

    @Override
    public ObjectOutputStream constructOutputStream(Source<?, ?> source) throws IOException {
        return new ObjectOutputStream(source.getOutputStream());
    }
}