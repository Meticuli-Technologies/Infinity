package com.meti.lib.source;

import java.io.IOException;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public interface ParentSource<S extends Source> extends Source {
    Set<? extends S> getSubSources() throws IOException;
}
