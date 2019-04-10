package com.meti.lib.fx;

import com.meti.lib.collect.State;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/7/2019
 */
class ControllerCallbackTest {
    private ControllerCallback callback;
    private State state;

    @BeforeEach
    void beforeEach() {
        state = new State();
        callback = new ControllerCallback(state);
    }

    @Test
    void callInvalidConstruct() {
        assertNull(callback.call(InvalidConstructor.class));
    }

    @Test
    void callNotAssignable() {
        assertThrows(IllegalArgumentException.class, () -> callback.call(NotAssignable.class));
    }

    @Test
    void callValidConstructor() {
        Object token = callback.call(ValidConstructor.class);
        assertEquals(ValidConstructor.class, token.getClass());

        ValidConstructor controller = (ValidConstructor) token;
        assertEquals(state, controller.state);
    }

    private static class NotAssignable {
    }

    private static class InvalidConstructor extends Controller {
        InvalidConstructor(State state) {
            super(null, state);
            throw new IllegalStateException();
        }
    }

    private static class ValidConstructor extends Controller {
        ValidConstructor(State state) {
            super(Mockito.mock(Stage.class), state);
        }
    }
}