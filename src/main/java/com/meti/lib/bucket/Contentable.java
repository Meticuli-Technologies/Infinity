package com.meti.lib.bucket;

import com.meti.lib.util.CollectionUtil;

import java.util.Collection;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public interface Contentable<T, C extends Collection<T>> {
    default T toSingle() {
        return CollectionUtil.toSingle(getContent());
    }

    C getContent();
}
