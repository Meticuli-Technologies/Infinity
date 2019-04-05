package com.meti.lib;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class StateTest {
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
    void byClass(){
        State state = new State();
        state.addAll(Arrays.asList("test", '0', 0));

        List<String> strings = state
                .byClass(String.class)
                .collect(Collectors.toList());

        assertEquals(1, strings.size());
        assertEquals("test", strings.get(0));
    }

}
