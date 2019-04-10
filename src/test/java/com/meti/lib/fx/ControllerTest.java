package com.meti.lib.fx;

import com.meti.lib.collect.State;
import org.junit.jupiter.api.Test;

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
        Controller controller = new Controller(state);
        assertEquals(controller.state, state);
    }
}
