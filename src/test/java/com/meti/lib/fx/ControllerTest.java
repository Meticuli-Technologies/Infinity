package com.meti.lib.fx;

import com.meti.lib.collect.State;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/7/2019
 */
class ControllerTest {
    @Test
    void construct(){
        State state = new State();
        Stage stage = Mockito.mock(Stage.class);
        Controller controller = new Controller(state, stage);
        assertEquals(stage, controller.stage);
        assertEquals(controller.state, state);
    }
}
