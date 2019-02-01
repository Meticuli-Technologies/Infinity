package com.meti.app.control.connect;

import com.meti.lib.net.Connection;

import java.util.function.Supplier;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/31/2019
 */
public interface ConnectionCreator<C extends Connection<?, ?, ?>> extends Supplier<C> {
}
