package com.meti.lib.source;

import java.io.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public interface ObjectSourceImpl extends CompoundSource<ObjectInputStream, ObjectOutputStream> {
    ObjectImpl getImplementation(boolean shared) throws IOException;
}
