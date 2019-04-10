package com.meti.lib.util;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OptionalUtilTest {

    @Test
    void throwIfPresentNotPresent() {
        assertDoesNotThrow(()-> OptionalUtil.throwIfPresent(Optional.empty()));
    }

    @Test
    void throwIfPresentWithPresent(){
        Exception exception = new Exception();
        try {
            OptionalUtil.throwIfPresent(Optional.of(exception));
            fail("No exception was thrown.");
        } catch (Exception e) {
            assertEquals(RuntimeException.class, e.getClass());
            assertEquals(exception, e.getCause());
        }
    }
}