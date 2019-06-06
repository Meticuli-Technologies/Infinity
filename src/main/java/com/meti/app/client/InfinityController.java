package com.meti.app.client;

import com.meti.app.Controls;
import com.meti.app.Toolkit;
import com.meti.lib.collect.State;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/3/2019
 */
@SuppressWarnings("ProtectedField")
public class InfinityController extends AdvancedController {
    protected final State state;
    protected final Toolkit toolkit;

    public InfinityController(Controls controls) {
        super(controls);
        this.state = controls.getState();
        this.toolkit = controls.getToolkit();
    }
}
