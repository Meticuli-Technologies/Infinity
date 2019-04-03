package com.meti.lib.net.query;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/30/2019
 */
public interface Query<T extends Response> extends Serializable {
    Class<T> getReturnClass();
}
