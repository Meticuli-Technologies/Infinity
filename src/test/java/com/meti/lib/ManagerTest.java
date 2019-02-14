package com.meti.lib;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/13/2019
 */
class ManagerTest {

    @Test
    void allocate() {
        Manager<String> manager = new Manager<>() {
            @Override
            protected String create() {
                return "test";
            }
        };

        String allocated = manager.allocate();
        assertEquals("test", allocated);
        assertEquals(1, manager.size());
        assertTrue(manager.contains("test"));
    }
}