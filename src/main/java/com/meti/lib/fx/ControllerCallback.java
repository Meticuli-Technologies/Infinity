package com.meti.lib.fx;

import com.meti.lib.collect.State;
import javafx.util.Callback;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/7/2019
 */
class ControllerCallback implements Callback<Class<?>, Object> {
    private final State state;

    ControllerCallback(State state) {
        this.state = state;
    }

    @Override
    public Object call(Class<?> param) {
        if (Controller.class.isAssignableFrom(param)) {
            try {
                return param.getDeclaredConstructor(State.class).newInstance(state);
            } catch (Exception e) {
                return null;
            }
        } else {
            throw new IllegalArgumentException(param + " does not extend " + Controller.class);
        }
    }
}
