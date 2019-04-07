package com.meti.lib.fx;

import com.meti.lib.collect.State;
import javafx.fxml.FXMLLoader;

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

    }

}
