package com.meti.lib.fx.state;

import com.meti.lib.State;
import javafx.util.Callback;

public class StateControllerFactory implements Callback<Class<?>, Object> {
    private final State state;

    public StateControllerFactory(State state) {
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
