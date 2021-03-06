package com.meti.lib.asset.source;

import java.io.IOException;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public interface Parent<T> {
    Set<T> getChildren() throws IOException;
}
