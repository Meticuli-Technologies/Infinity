package com.meti.app.control.connect;

import com.meti.lib.fx.AbstractExternalFXML;
import com.meti.lib.fx.Controller;
import com.meti.lib.net.Connection;

import java.util.function.Supplier;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/31/2019
 */
public abstract class ConnectionCreator<T extends Controller, C extends Connection<?, ?, ?>> extends AbstractExternalFXML<T> implements Supplier<C> {
    protected abstract String getName();
}
