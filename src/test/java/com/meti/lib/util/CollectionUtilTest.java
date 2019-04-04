package com.meti.lib.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static com.meti.lib.util.CollectionUtil.*;
import static java.util.Collections.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/4/2019
 */
class CollectionUtilTest {

    @Test
    void toSingleEmpty() {
        Optional<Object> optional = toSingle(emptySet());
        assertFalse(optional.isPresent());
    }

    @Test
    void toSingleOne(){
        Optional<String> optional = toSingle(singleton("test"));
        assertTrue(optional.isPresent());
        assertEquals("test", optional.get());
    }

    @Test
    void toSingleMultiple(){
        assertThrows(IllegalArgumentException.class, () -> toSingle(Arrays.asList("test0", "test1")));
    }
}