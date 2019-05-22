package com.meti.lib.source;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public interface ParentSource<S extends Source> extends Source {
    S getSubSources();
}
