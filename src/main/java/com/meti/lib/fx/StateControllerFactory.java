package com.meti.lib.fx;

import com.meti.lib.State;
import javafx.util.Callback;

class StateControllerFactory implements Callback<Class<?>, Object> {
    private final State state;

    StateControllerFactory(State state) {
        this.state = state;
    }

    @Override
    public Object call(Class<?> controllerClass) {
        if (StateController.class.isAssignableFrom(controllerClass)) {
            return createController(controllerClass);
        } else {
            throw new IllegalArgumentException(controllerClass + " is not assignable to " + StateController.class);
        }
    }

    private Object createController(Class<?> controllerClass) {
        try {
            return controllerClass.getDeclaredConstructor(State.class).newInstance(state);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
