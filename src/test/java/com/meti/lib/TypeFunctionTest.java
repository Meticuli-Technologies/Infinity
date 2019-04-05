package com.meti.lib;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.function.Function;

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

    private class TypeFunction<T> implements Function<Object, T> {
        private final Class<T> tClass;

        public TypeFunction(Class<T> tClass) {
            this.tClass = tClass;
        }

        @Override
        public T apply(Object o) {
            return null;
        }
    }
}
