package com.meti.lib.net.client;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/31/2019
 */
public interface ObjectReader {
    Object read() throws IOException, ClassNotFoundException;
}
