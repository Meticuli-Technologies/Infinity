package com.meti.lib;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class CollectionConsumerTest {
    @Test
    void accept(){
        ArrayList<String> list = new ArrayList<>();
        new CollectionConsumer<>(list).accept("test");

        assertEquals(1, list.size());
        assertEquals("test", list.get(0));
    }

}
