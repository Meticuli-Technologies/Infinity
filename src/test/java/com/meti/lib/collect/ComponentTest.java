package com.meti.lib.collect;

import com.meti.lib.collect.Component;
import com.meti.lib.collect.event.Event;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class ComponentTest {
    @Test
    void construct(){
        Component<Enum<?>, Event> component = new Component<>();
        assertNotNull(component.eventManager);
    }

}
