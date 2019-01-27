package com.meti.lib.fx;

import com.meti.lib.state.State;
import com.meti.lib.util.Singleton;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class Controller<S extends State> {
    private final Singleton<S> state = new Singleton<>();
}
