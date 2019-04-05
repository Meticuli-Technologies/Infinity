package com.meti.lib;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class TypePredicateTest {
    @Test
    void test(){
        TypePredicate<List> predicate = new TypePredicate<>(List.class);

        /*
        Here we test 3 cases.
        1) The two classes are equal.
        2) The supplied class contains a subclass.
        3) The supplied class is not an instance of the tested class.
         */
        assertTrue(predicate.test(Mockito.mock(List.class)));
        assertTrue(predicate.test(new ArrayList<>()));
        assertFalse(predicate.test(0));
    }

}
