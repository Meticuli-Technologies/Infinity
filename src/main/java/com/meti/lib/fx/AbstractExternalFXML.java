package com.meti.lib.fx;

import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/1/2019
 */
public abstract class AbstractExternalFXML<C extends Controller> implements ExternalFXML<C> {
    protected C controller;

    @Override
    public void accept(C c) {
        this.controller = c;
    }

    public Optional<C> getController() {
        return Optional.ofNullable(controller);
    }
}
