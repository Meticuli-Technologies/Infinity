package com.meti.lib.collect;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class StateTest {
    @Test
    void byClass() {
        State state = new State();
        state.addAll(Arrays.asList("test", '0', 0));

        List<String> strings = state
                .byClass(String.class)
                .collect(Collectors.toList());

        assertEquals(1, strings.size());
        assertEquals("test", strings.get(0));
    }

    @Test
    void byPredicate() {
        //Make a state and add a few integers.
        State state = new State();
        state.addAll(Arrays.asList(0, 1, 2, 3, 4, 5));

        List<Object> objects = state
                //Check for all integers greater than 3.
                .byPredicate(o -> o instanceof Integer && ((Integer) o) > 3)
                .collect(Collectors.toList());

        //Check the contents.
        assertEquals(2, objects.size());
        assertEquals(4, objects.get(0));
        assertEquals(5, objects.get(1));
    }

    @Test
    void construct() {
        /*
        This compiler detects State as a catcher, and therefore gets confused.
         */
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        State state = new State("test0", "test1", "test2");
        assertEquals(3, state.size());
        assertTrue(state.contains("test0"));
        assertTrue(state.contains("test1"));
        assertTrue(state.contains("test2"));
    }
}
