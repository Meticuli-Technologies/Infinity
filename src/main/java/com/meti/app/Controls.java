package com.meti.app;

import com.meti.lib.collect.State;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/3/2019
 */
public interface Controls {
    NetBinding getNetBinding();
    State getState();
    Toolkit getToolkit();
}
