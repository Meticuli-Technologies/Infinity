package com.meti.lib.bucket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/30/2019
 */
class ContentableTest {

    @Test
    void toSingle() {
        Assertions.assertEquals("test", ((Contentable<String, Set<String>>) () -> Collections.singleton("test")).toSingle());
    }
}