package com.meti.lib;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class TypeFunctionTest {
    @Test
    void apply(){
        TypeFunction<String> function = new TypeFunction<>(String.class);

        assertEquals("test", function.apply("test"));
        assertThrows(ClassCastException.class, () -> function.apply(0));
    }

}
