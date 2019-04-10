package com.meti.lib.fx;

import com.meti.lib.collect.State;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/7/2019
 */
class ControllerCallback implements Callback<Class<?>, Object> {
    private final State state;
    private Stage stage;

    ControllerCallback(State state, Stage stage) {
        this.state = state;
        this.stage = stage;
    }

    @Override
    public Object call(Class<?> param) {
        if (Controller.class.isAssignableFrom(param)) {
            try {
                //Make sure this constructor equals the same in Controller
                return param.getDeclaredConstructor(State.class, Stage.class)
                        .newInstance(state, stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException(param + " does not extend " + Controller.class);
        }
    }
}
