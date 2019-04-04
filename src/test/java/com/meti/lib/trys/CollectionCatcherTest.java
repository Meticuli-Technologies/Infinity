package com.meti.lib.trys;

import com.meti.lib.util.ExceptionUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/4/2019
 */
class CollectionCatcherTest {

    @Test
    void throwAll() {
        NullPointerException e0 = new NullPointerException();
        IllegalArgumentException e1 = new IllegalArgumentException();

        CollectionCatcher<ArrayList<Exception>> catcher = new CollectionCatcher<>(new ArrayList<>());
        catcher.accept(e0);
        catcher.accept(e1);

        try {
            catcher.throwAll();

            fail("An exception was not thrown.");
        } catch (Exception e) {
            assertEquals(ExceptionUtil.compound(Arrays.asList(e0, e1)).getMessage(), e.getMessage());
        }
    }
}