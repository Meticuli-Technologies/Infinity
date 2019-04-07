package com.meti.lib.fx;

import com.meti.lib.collect.State;
import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/7/2019
 */
public class ControllerLoaderTest {

    private class ControllerLoader extends FXMLLoader {
        private final State state;

        public ControllerLoader(State state) {
            this.state = state;
            setControllerFactory(new ControllerCallback(state));
        }

        private class ControllerCallback implements Callback<Class<?>, Object> {
            private final State state;

            ControllerCallback(State state) {
                this.state = state;
            }

            @Override
            public Object call(Class<?> param) {
                if (Controller.class.isAssignableFrom(param)) {
                    try {
                        return param.getConstructor(State.class).newInstance(state);
                    } catch (Exception e) {
                        return null;
                    }
                } else {
                    throw new IllegalArgumentException(param + " does not extend " + Controller.class);
                }
            }
        }
    }
}
