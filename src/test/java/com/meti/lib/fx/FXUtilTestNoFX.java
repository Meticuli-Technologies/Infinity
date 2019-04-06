package com.meti.lib.fx;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/6/2019
 */
class FXUtilTestNoFX {

    @Test
    void throwIfNotFX() {
        assertThrows(IllegalStateException.class, FXUtil::throwIfNotFX);
    }
}